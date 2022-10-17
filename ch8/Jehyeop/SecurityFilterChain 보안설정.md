# SecurityConfiguration

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
## httpSecurity.csrf()

<img width="520" alt="Spring Security_img7" src="https://user-images.githubusercontent.com/90807141/196109909-4212d99c-260e-4754-b485-c08a5e5942e4.png">

### CSRF 란?

→ CSRF(Cross-Site Request Forgery) : 사이트간 요청 위조 (직역) , 원클릭 공격 or 세션 라이팅 이라 부른다.

- 웹사이트가 신뢰하는 사용자로 부터 권한 없는 요청을 전송
- 인증된 사용자의 브라우저에서 사이트가 갖는 신뢰를 악의적인 공격에 사용
- 링크, 스크립트를 사용하여 사용자가 인증된 대상 사이트로 원하지 않는 HTTP 요청을 전송

### 보안 방법

1. 시크릿 토큰 유효성 검증(”Synchronizer Token Patten”) → 특정 관리 조작 요청이 시크릿 토큰을 포함하도록 한다.
2. 참조자 유효성 검증 → HTTP 요청에 referer헤더의 유효성을 검증하도록 한다. ( WebSEAL이 referer헤더와 allowed-referers 들을 비교하여 유효성 검증 )
3. 원하지 않는 인증 요청 거부 → 원하지 않는 로그인 요청 거부

### 사용 하지 않아도 되는 이유

1. Rest API 를 이용한 서버라면 Session 기반 인증과는 다르게 Statless 하기 때문에 서버에 인증정보를 보관하지 않는다.

   → rest api 는 Cilent 권한이 필요한 요청을 하기위한 인증 정보를 OAuth2, jwt 토큰에 포함

2. Spring Security는 기본적으로 csrf protection을 제공한다.

---

## httpSecurity.headers().frameOption()

### X-Frame-Options

→ 웹 어플리케이션에 HTML 삽입 취약점이 존재하면 공격자는 다른 서버에 위치한 페이지를 `<frame>`, `<iframe>`, `<object>` 등으로 삽입하여 다양한 공격에 사용할 수 있다.

피해자의 입장에서는 링크를 눌렀을 때 의도 했던 것과는 다른 동작을 하게 한다하여 이를 **클릭재킹(Clickjacking)** 이라 부른다.

웹 페이지를 공격에 필요한 형태로 조작하기 때문에 "사용자 인터페이스 덧씌우기"(User Interface redress) 공격 이라고도 부른다.

- DENY : “이 홈페이지는 다른 홈페이지에서 표시 할 수 없음” → 해당 페이지는 frame 내에서 표시 할 수 없다.
- SAMEORIGIN : “이 홈페이지는 동일한 도메인의 페이지 내에서만 표시할 수 있음” → 해당 페이지와 동일한 origin에 해당하는 frame 내에서 표시를 허용한다.
- ALLOW-FROM origin : “이 홈페이지는 origin 도메인의 페이지에서 포함하는 것을 허용함” → 해당 페이지는 지정된 origin에 해당하는 frame 내에서 표시할 수 있다.

---

## httpSecurity.authorizeRequests()
- 시큐리티 처리에 HttpServletRequest를 이용한다는 것을 의미

**추가 설정**

`.antMatchers(”/경로/**”, "/경로2/**")` : 특정 리소스에 대한 권한 설정

`.permitAll()` : 모든 사용자 접근 가능

`.denyAll()` : 모든 사용자 접근 제한

`.hasRole("이름")` : 시스템상에서 특정 권한을 가진 사람만이 접근 설정

`.anyRequest().authenticated()` : 모든 리소스를 의미, 접근허용 리소스 및 인증 후 특정 레벨의 권한을 가진 사용자만 접근가능한 리소스를 설정하고 그외 나머지 리소스들은 무조건 인증을 완료해야 접근이 가능

---

## httpSecurity.formLogin()

### FormLogin

→ form 태그 기반의 로그인을 지원해주는 설정

- 별도로 로그인 페이지를 제작하지 않아도 된다.

  → ‘/login’ 경로를 호출하면 SpringSecurity에서 제공하는 기본 로그인 화면을 볼 수 있다.


**추가 설정**

`.loginPage("login.html")` : 설정한 로그인 페이지 사용시

`.loginProcessingUrl(”/perform_login”)` : 사용자 이름과 암호를 제출할 URL 설정 → 인증 처리를 하는 URL을 설정

`.defaultSuccessUrl(”/main”)` : 정상적으로 인증 성공 했을 경우 이동하는 페이지

`.successHandler(new CustomAuthenticationSuccessHandler(”/main”)` : 정상적인증 성공 후 별도의 처리가 필요한 경우

`.failureUrl(”/login_fail”)` : 로그인 실패 후 방문 페이지

`.failureHandler(authenticationFailureHandler(”/login_fail”))` : 실패 후 별도의 처리가 필요한 경우

---

## httpSecurity.logout()

### Logout

→ Client에서 GET방식의 /logout 리소스 호출
→ Server에서 세션무효화, 인증토큰 삭제, 쿠키정보 삭제 후 로그인페이지로 리다이렉트

**추가 설정**

`.logout()` : 로그아웃 처리
`.logoutUrl("/logout")` : 로그아웃 처리 URL
`.logoutSuccessUrl("/login")` : 로그아웃 성공 후 이동페이지
`.deleteCookies("JSESSIONID","remember-me")` : 로그아웃 후 쿠키 삭제
`.addLogoutHandler(new LogoutHandler())` : 로그아웃 핸들러
`.logoutSuccessHandler(new LogoutSuccessHandler())` : 로그아웃 성공 후 핸들러
`.deleteCookies("remember-me")` : 쿠키 삭제

---

## httpSecurity.rememberMe()

### Remamber Me 인증

- 세션이 만료되고 웹 브라우저가 종료된 후에도 어플리케이션이 사용자를 기억하는 기능 ( 로그인 정보를 기억하는 설정 )

  → 로그인한 사용자만 접근 가능

- **Remember-Me** 쿠키에 대한 HTTP 요청을 확인한 후 토큰 기반 인증을 사용해 유효성을 검사하고 토큰이 검증되면 사용자는 로그인 된다.
- **사용자 라이프 사이클**
    - 인증 성공(Remember-Me 쿠키 설정)
    - 인증 실패(쿠키가 존재하면 쿠키 무효화)
    - 로그아웃(쿠키가 존재하면 쿠키 무효화)

**추가 설정**

`.rememberMe()` : rememberMe기능 작동
`.rememberMeParameter("remember-me")` : 기본 파라미터명은 remember-me
`.tokenValiditySeconds(3600)` : default는 14일
`.alwaysRemember(true)` : remember me 기능이 활성화되지 않아도 항상 실행. default false
`.userDetailsService(userDetailsService)` : Remember me에서 시스템에 있는 사용자 계정을 조회할때 사용할 클래스

<br>

***위 방법 이외에도 여러 설정이 가능하다.**