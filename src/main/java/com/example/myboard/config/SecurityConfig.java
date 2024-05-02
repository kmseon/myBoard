package com.example.myboard.config;

import jakarta.persistence.Id;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //설정 타입 인식
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean //controller service repository conponant 컨포넌트 의존성 생성
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((request)->request
                        .requestMatchers("/css/**","/js/**", "/images/**").permitAll()
                        .requestMatchers("/user/**").permitAll()
//                        .requestMatchers("/**").permitAll())
                        .anyRequest().authenticated())

                .formLogin((form)->form
                        .loginPage("/user/login")
                        .loginProcessingUrl("/login")
//                        .usernameParameter("email") :쿼리 사용해서 잡음
                        .defaultSuccessUrl("/"))

                .logout((out)->out
                        .logoutSuccessUrl("/")
                        .logoutUrl("/logout")
                );
        return http.build();
    }
}
