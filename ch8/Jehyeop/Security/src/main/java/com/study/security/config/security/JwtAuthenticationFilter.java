package com.study.security.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        // servletRequest 에서 token 추출
        String token = jwtTokenProvider.resolveToken(servletRequest);
        log.info("[doFilterInternal] token 값 추출 완료. token : {}", token);

        log.info("[dofilterInternal] token 값 유효성 체크 시작");
        // token 이 유효하다면 Authentication 객체를 생성해서 SecurityContextHolder 에 추가
        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.info("[doFilterInternal] token 값 유효성 체크 완료");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
