package com.tmp.xmash.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // 테스트 환경에서 CSRF 비활성화
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/h2-console/**").permitAll() // H2 콘솔 접근 허용
                        .requestMatchers("/login").permitAll()
                        .anyRequest().authenticated())
//                .formLogin(formLogin -> formLogin
//                        .loginPage("/login")
//                        .permitAll())
                .logout((logout) -> logout
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 세션 필요시 생성
                        .sessionFixation().newSession() // 세션 고정 공격 방지
                        .maximumSessions(1) // 최대 세션 수를 1로 설정 (옵션)
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
