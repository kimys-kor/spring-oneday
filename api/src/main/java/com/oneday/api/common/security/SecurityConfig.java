package com.oneday.api.common.security;

import com.oneday.api.common.jwt.JwtAuthenticationFilter;
import com.oneday.api.common.jwt.JwtAuthorizationFilter;
import com.oneday.api.repository.MemberRepository;
import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity // 시큐리티 활성화 -> 기본 스프링 필터체인에 등록
public class SecurityConfig {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CorsConfig corsConfig;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .apply(new MyCustomDsl()) // 커스텀 필터 등록
                .and()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                .authorizeRequests(authroize -> authroize.requestMatchers("/member/view/**")
                        .access("hasRole('ROLE_MEMBER') or hasRole('ROLE_ADMIN')")
                        .requestMatchers("/admin/**")
                        .access("hasRole('ROLE_ADMIN')")
                        .anyRequest().permitAll())
                .build();
    }

    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http
                    .addFilter(corsConfig.corsFilter())
                    .addFilter(new JwtAuthenticationFilter(authenticationManager))
                    .addFilter(new JwtAuthorizationFilter(authenticationManager, memberRepository));
        }
    }

    @Bean
    public BCryptPasswordEncoder encoderPwd() {
        return new BCryptPasswordEncoder();
    }

}