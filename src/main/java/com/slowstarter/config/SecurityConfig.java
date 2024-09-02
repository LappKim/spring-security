package com.slowstarter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig
{
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
    {
        /**
         * 접근권한 설정
         */
        httpSecurity
            .authorizeHttpRequests( (customizer) ->
                customizer.requestMatchers("/").permitAll()
                          .requestMatchers("/login", "/loginProc").permitAll()
                          .requestMatchers("/join", "/joinProc").permitAll()
                          .requestMatchers("/admin").hasRole("ADMIN")
                          .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
                          .anyRequest()
                          .authenticated()
            );

        /**
         * 로그인 페이지 설정
         */
        httpSecurity
            .formLogin( (customizer) ->
                customizer.loginPage("/login")
                          .loginProcessingUrl("/loginProc")
                          .permitAll()
            );

        /**
         * logout
         */
        httpSecurity
            .logout( (customizer) ->
                customizer.logoutUrl("/logout")
                          .logoutSuccessUrl("/")
            );

//        /**
//         * CSRF
//         */
//        httpSecurity
//            .csrf( (customizer) ->
//                customizer.disable()
//            );

        /**
         * Session 관리
         */
        httpSecurity
            .sessionManagement( (customizer) ->
                customizer.maximumSessions(1)
                          .maxSessionsPreventsLogin(false)
                          .expiredUrl("/login?expired")
            );

        /**
         * none(): 로그인 시 세션 정보 변경 안함
         * newSession() : 로그인 시 세션 새로 생성
         * changeSessionId() : 로그인 시 동일한 세션에 대한 id 변경
         */
        httpSecurity
        .sessionManagement( (customizer) ->
            customizer.sessionFixation()
                      .changeSessionId()
        );

        return httpSecurity.build();
    }
}
