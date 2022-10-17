# Spring Security 인증처리

## 인증처리 (UsernamePasswordAuthenticationFilter)
<img width="828" alt="Spring Security_img3" src="https://user-images.githubusercontent.com/90807141/196105154-7d9ff53c-2188-4000-9498-73225b25be43.png">

1. 클라이언트로부터 요청을 받으면 서블릿 필터에서 SecurityFilterChain으로 작업을 위임되고 그 중 UsernamePasswordAuthenticationFilter 에서 인증 처리
2. 요청 객체에서 Username, Password를 추출해 **Token**을 생성
3. 생성된 **Token**을 AuthenticationManager로 전달 → 구현체인 ProviderManager에서 처리
4. ProviderManager는 인증을 위해 AuthenticationProvider로 **Token**을 전달
5. AuthenticationProvider는 **Token** 정보를 UserDetailsService로 전달
6. UserDetailsService는 전달받은 정보를 통해 데이터베이스에서 일치하는 사용자를 찾아 UserDetails 객체를 생성
7. 생성된 UserDetails 객체는 AuthenticationProvider로 전달 되며 해당 Provider는 인증을 수행하고 성공하면 ProviderManager로 **권한을 담은 Token**을 전달.
8. ProviderManager는 **검증된 Token**을 AuthenticationFilter로 전달.
9. AuthenticationFilter는 **검증된 Token**을 SecurityContextHolder에 있는 SecurityContext에 저장.

## 설정 과정

### 기본 설정

Gradle dependencies 설정

- implementation 'org.springframework.boot:spring-boot-starter-security'
- implementation 'io.jsonwebtoken:jjwt:0.9.1'

### UserDetails, UserDetailsService 구현

: 필요한 정보를 UserDetail에 담아 AuthenticationProvider로 보내준다.

<img width="828" alt="Spring Security_img4" src="https://user-images.githubusercontent.com/90807141/196105164-5da76c8b-ac91-4a9e-9207-c296b67ce4a3.png">

**User.java**

```java
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails {

...

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
		    return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
		}

		//security 에서 사용하는 회원 구분 id
		@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
		@Override
		public String getUsername() {
		    return this.uid;
		}
		
		//계정이 만료되었는지 체크하는 로직
		@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
		@Override
		public boolean isAccountNonExpired() {
		    return true;
		}
	
		//계정이 잠겼는지 체크하는 로직
		@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
		@Override
		public boolean isAccountNonLocked() {
		    return true;
		}

		//계정의 패스워드가 만료되었는지 체크하는 로직
		@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
		@Override
		public boolean isCredentialsNonExpired() {
		    return true;
		}

		//계정이 사용가능한지 체크하는 로직
		@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
		@Override
		public boolean isEnabled() {
		    return true;
		}
}
```

**UserRepository**

```java
public interface UserRepository extends JpaRepository<User, Long> {
    // id 값을 토큰 생성 정보로 사용
    User getByUid(String uid);
}
```

**UserDetailsServiceImpl**

```java
@RequiredArgsConstructor
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    // User 엔티티의 id 값 가져오기
    @Override
    public UserDetails loadUserByUsername(String username){
        log.info("[loadUserByUsername] loadUserByUsername 수행. username : {}", username);
        return userRepository.getByUid(username);
    }
}
```

**UserDetailsService**

```java
public interface UserDetailsService {
    // UserDetails = User (구현체)
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
```

---

### JwtTokenProvider 구현

<img width="828" alt="Spring Security_img5" src="https://user-images.githubusercontent.com/90807141/196105172-b7c7de0a-51ab-4cd7-a7c8-ec1e694a9dce.png">

**JwtTokenProvider**

<설정 기능>

- SecretKey 초기화 (Base64 형식으로 인코딩)
- Token 생성
- SecurityContextHolder에 저장할 Authentication 생성
    - 토큰의 sub 값 추출
- 파라미터로 받아 헤더값으로 전달된 "X_AUTH_TOKEN" 추출
- Token 유효기간 체크

```java
import com.study.security.service.UserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

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
```

---

### JwtAuthentication 구현

<img width="828" alt="Spring Security_img6" src="https://user-images.githubusercontent.com/90807141/196105173-75640f21-ad5b-4ac1-a18f-413c724af4e9.png">

**JwtAuthenticationFilter**

- JwtTokenProvider를 통해 servletRequest에서 토큰을 추출 후 유효성 검사
- 토큰이 유효하면 Authentication 객체를 생성하여 SecurityContextHolder에 추가

```java
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

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
```

→ doFilter 기준 앞에 작성한 코드와 뒤에 작성한 코드는 서블릿 실행 전 실행, 후 실행 이다.

---

### SecurityConfiguration 구현

```java
@Configuration
@EnableWebSecurity
public class WebSecurityConfigure {

		private final JwtTokenProvider jwtTokenProvider;

		@Autowired
    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

		@Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic().disable()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS)

                ...(보안 설정)...

                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
```