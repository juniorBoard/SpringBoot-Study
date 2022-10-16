package com.study.security.config.security;

import com.study.security.service.UserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final UserDetailsService userDetailsService;

    @Value("${springboot.jwt.secret}")
    private String secretKey; // 토큰 생성에 필요한 key
    private final long tokenValidMillisecond = 100 * 60 * 60; // token 유효시간

    // SecretKey 초기화
    @PostConstruct // Bean 객체로 주입된 후 수행
    protected void init() {
        log.info("[init] JwtTokenProvider 내 secretKey 초기화 시작");

        // secretKey 를 base64 형식으로 인코딩
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));

        log.info("[init] JwtTokenProvider 내 secretKey 초기화 완료");
    }

    public String createToken(String userUid, List<String> roles) {
        log.info("[createToken] 토큰 생성 시작");

        Claims claims = Jwts.claims().setSubject(userUid); // Claims 객체에 담아 Jwt Token 의 내용에 값 넣기, sub 속정에 값 추가(Uid 사용)
        claims.put("roles", roles); // 사용자 권한확인용 추가
        Date now = new Date();

        // Token 생성
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        log.info("[createToken] 토큰 생성 완료");
        return token;
    }

    // 필터에서 인증에 성공시 SecurityContextHolder 에 저장할 Authentication 생성
    public Authentication getAuthentication(String token) {
        log.info("[getAuthentication] 토큰 인증 정보 조회 시작");

        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUsername(token));

        log.info("[getAuthentication] 토큰 인증 정보 조회 완료, UserDetails UserName : {}", userDetails.getUsername());

        return new UsernamePasswordAuthenticationToken(userDetails, " ", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        log.info("[getUsername] 토큰 기반 회원 구별 정보 추출");

        // 토큰을 생성할때 넣었던 sub 값 추출
        String info = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();

        log.info("[getUsername] 토큰 기반 회원 구별 정보 완료, info : {}", info);

        return info;
    }

    // 파라미터로 받아 헤더값으로 전달된 "X_AUTH_TOKEN" 추출
    public String resolveToken(HttpServletRequest request) {
        log.info("[resolveToken] HTTP 헤더에서 Token 값 추출");

        return request.getHeader("X-AUTH-TOKEN");
    }

    // Token 유효기간 체크
    public boolean validateToken(String token) {
        log.info("[validateToken] 토큰 유효 체크 시작");

        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());

        } catch (Exception e) {
            log.info("[validateToken] 토큰 유효 체크 예외 발생");
            return false;
        }
    }
}
