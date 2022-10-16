package com.study.security.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtTokenProvider jwtTokenProvider;

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return web ->
//            web.ignoring()
//                    .antMatchers("/v2/api-docs", "/swagger-resources", "/swagger*/**", "/swagger-ui.html",
//                            "/webjars/**", "/swagger/**", "/sign-api/exception");
//    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic().disable() // UI를 사용하는 것을 기본값으로 가진 시큐리티 설정 비활성화
                .csrf().disable()
                .sessionManagement() // Rest Api 기반 애플리케이션 동작 방식 설정
                .sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS) // 세션을 사용하지 않아 STATELESS 로 설정

                .and()
                .authorizeRequests() // 사용 권한 설정
                .antMatchers("/sign-api/sign-in", "/sign-api/sign-up", "/sign-api/exception").permitAll() // 권한 허용 URL 설정
                .antMatchers("/v2/api-docs", "/swagger-resources", "/swagger*/**", "/swagger-ui.html", "/webjars/**",
                        "/swagger/**", "/sign-api/exception").permitAll() // 권한 허용 URL 설정
                .antMatchers(HttpMethod.GET, "/product/**").permitAll() // product 로 시작하는 GET 요청 허용
                .antMatchers("**exception**" ).permitAll() // 'exception' 단어가 들어간 경로는 모두 허용
                .anyRequest().hasRole("ADMIN") // 기타 요청은 인증 권한을 가진 사용자에게 허용

                .and()
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler()) // 권한 확인 과정에서의 예외 발생처리
                .and()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint()) // 인증과정에서의 예외처리
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class); // JWT Token 필터를 id/password 인증 필터 이전에 추가

        return httpSecurity.build();
    }
}
