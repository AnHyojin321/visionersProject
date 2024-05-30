package com.example.visioners.config;

import com.example.visioners.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests

                .requestMatchers("/", "/header.html", "/home", "/main", "/css/**", "/font/**",
                        "/images/**", "/js/**", "/signup", "js/start.js", "/static/**", "/api/check-username").permitAll() // 인증 없이 접근 허용

                                .requestMatchers("/home", "/login**", "/signup**", "js/start.js", "/static/**").permitAll() // 인증 없이 접근 허용

                .anyRequest().authenticated() // 그 외 모든 경로는 인증 필요
                )
                .formLogin(formLogin ->
                formLogin
                        .loginPage("/login") // 로그인 페이지 설정
                        .defaultSuccessUrl("/main", true) // 로그인 성공 시 리다이렉트할 URL 설정
                        .failureUrl("/login?error=true") // 로그인 실패 시 리다이렉트할 URL 설정
                        .permitAll() // 로그인 폼에 대한 접근을 허용
        )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout") // 로그아웃 URL 설정
                                .logoutSuccessUrl("/home") // 로그아웃 성공 시 리다이렉트할 URL 설정
                                .permitAll() // 로그아웃 URL에 대한 접근을 허용
                )
                .csrf().disable(); // CSRF 보호 비활성화 (개발 시에만, 실제 배포 시에는 활성화 권장)

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}