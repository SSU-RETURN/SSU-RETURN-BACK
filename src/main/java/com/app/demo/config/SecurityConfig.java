package com.app.demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
/*
    private final JwtTokenProvider jwtTokenProvider;
    public SecurityConfig(JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider = jwtTokenProvider;
    }
*/
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((auth)-> auth
                        .requestMatchers("/login","/join","joinProc","/swagger-ui/**", "/v3/api-docs/**", "/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")  // 로그인 페이지
                        .loginProcessingUrl("/loginProc")  // 로그인 처리 URL
                        .defaultSuccessUrl("/", true)  // 로그인 성공 시 리디렉션 경로
                        .permitAll()  // 모든 사용자가 로그인 페이지 접근 허용
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")  // 로그아웃 처리 URL
                        .logoutSuccessUrl("/login?logout")  // 로그아웃 성공 후 리디렉션 경로
                        .permitAll()  // 로그아웃 URL 접근 허용
                )
                .csrf(csrf -> csrf.disable()); // CSRF 보호 비활성화

        return http.build();
    }
}