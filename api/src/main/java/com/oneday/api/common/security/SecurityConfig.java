package com.oneday.api.common.security;

import com.oneday.api.common.jwt.JwtAuthenticationFilter;
import com.oneday.api.common.jwt.JwtAuthorizationFilter;
import com.oneday.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


import java.util.List;


@Configuration
@EnableWebSecurity // 시큐리티 활성화 -> 기본 스프링 필터체인에 등록
@RequiredArgsConstructor
public class SecurityConfig  {

    private final UserRepository userRepository;
    private final CorsConfig corsConfig;


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .apply(new MyCustomDsl(userRepository)) // 커스텀 필터 등록
                .and()
                .authorizeRequests(authroize -> authroize
                        .requestMatchers("/user/login")
                        .access("permitAll()")
                        .requestMatchers("/user/**")
                        .access("hasRole('ROLE_USER') or hasRole('ROLE_SHOP') or hasRole('ROLE_ADMIN')")
                        .requestMatchers("/shop/**")
                        .access("hasRole('ROLE_SHOP') or hasRole('ROLE_ADMIN')")
                        .requestMatchers("/admin/**")
                        .access("hasRole('ROLE_ADMIN')")
                        .anyRequest().permitAll())
                .exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                .build();
    }


    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
        private final UserRepository userRepository;

        public MyCustomDsl(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            JwtAuthenticationFilter filter = new JwtAuthenticationFilter(authenticationManager, userRepository);
            filter.setFilterProcessesUrl("/api/login");

            http
                    .addFilter(corsConfig.corsFilter())
                    .addFilter(filter) // Use the created JwtAuthenticationFilter
                    .addFilter(new JwtAuthorizationFilter(authenticationManager, userRepository));
        }
    }

    @Bean
    public BCryptPasswordEncoder encoderPwd() {
        return new BCryptPasswordEncoder();
    }


}