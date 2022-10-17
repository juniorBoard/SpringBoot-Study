# Spring Security

### Base 지식
- Authentication (인증) : 사용자가 누구인지 확인하는 단계
- Authorization (인가) : 리소스에 접근할 권리가 있는지를 확인하는 과정
- Principal (접근주체) : 애플리케이션의 기능을 사용하는 주체를 의미

## Spring Security 동작구조
- Servlet Filter를 기반으로 동작  
  <img width="592" alt="Spring Security_img1" src="https://user-images.githubusercontent.com/90807141/196105111-438fab8e-d4f9-4624-aabc-8cd0dd4eb55a.png">  
  *필터 체인 = ApplicationFilterChain

1. 클라이언트에서 애플리케이션으로 요청을 보낸다.
2. 서블릿 컨테이너는 URI를 확인해서 필터와 서블릿을 매핑
3. 필터체인을 서블릿 컨테이너의 필터 사이에서 동작 시키기 위해 DelegatingFilterProxy를 사용.  
   <DelegatingFilterProxy의 FilterProxy 구조>  
   <img width="333" alt="Spring Security_img2" src="https://user-images.githubusercontent.com/90807141/196105140-ffdbfbe4-41b2-487d-ae6d-a4b85e6ac782.png">  
- 서블릿 컨테이너의 생명주기와 ApplicationContext 사이에서 다리 역할을 수행하는 필터 구현체이다.
- 표준 필터를 구현하고 있으며, 역할을 위임할 **필터체인 프록시**를 내부에 가지고 있다.
- **필터체인 프록시**
    - 스프링 부트의 자동 설정에 의해 자동 생성된다.
    - 스프링 시큐리티에서 제공하는 필터로 보안 필터체인(SecurityFilterChain)을 통해 많은 보안 필터(SecurityFilter) 를 사용할 수 있다.
- **보안 필터** (SecurityFilter)
    - 보안 필터 체인에서 사용되는 필터는 여러 종류가 있으며 실행되는 순서가 다르다.
        <details>
          <summary>보안 필터의 종류(실행 순)</summary>
          <div makdown="1">
      
          1. Channel ProcessingFilter
          2. WebAsyncManagerIntergrationFilter
          3. SecurityContextPersistenceFilter
          4. HeaderWriterFilter
          5. CorsFilter
          6. CsrfFilter
          7. LogoutFilter
          8. OAuth2AuthorizationRequestRedirectFilter
          9. Sam12WebSsoAuthenticationRequestFilter
          10. X509AuthenticationFilter
          11. AbstractPreAuthenTicatedProcessingFilter
          12. CasAuthenticationFilter
          13. OAuth2LoginAuthenticationFilter
          14. Sam12WebSsoAuthenticationFilter
          15. UsernamePasswordAuthenticationFilter
          16. OpenIDAuthenticationFilter
          17. DefaultLoginPageGeneratingFilter
          18. DefaultLogoutPageGeneratingFilter
          19. ConcurrentSessionFilter
          20. DigestAuthenticationFilter
          21. BearerTokenAuthenticationFilter
          22. BasicAuthenticationFilter
          23. RequestCacheAwareFilter
          24. SecurityContextHolderAwareRequestFilter
          25. JaasApiIntegrationFilter
          26. RememberMeAuthenticationFilter
          27. AnonymousAuthenticationFilter
          28. OAuth2AuthorizationCodeGrantFilter
          29. SessionManagementFilter
          30. ExceptionTranslationFilter
          31. FilterSecurityInterceptor
          32. SwitchUserFilter
    
  - ~~WebSecurityConfigurerAdapter 클래스를 상속받아 설정할 수 있다.~~ → 이제 지원하지 않는다.   
    → @Bean으로 `SecurityFilterChain` 을 등록하여 사용한다.
    ```java
    @Configuration
    @EnableWebSecurity
    public class WebSecurityConfigure {

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.csrf().disable(); 

                    ...(보안 설정)...

                    return http.build();
        }
    }   
    ```