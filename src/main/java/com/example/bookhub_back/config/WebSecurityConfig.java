package com.example.bookhub_back.config;

import com.example.bookhub_back.dto.filter.JwtAuthenticationFilter;
import com.example.bookhub_back.provider.JwtTokenProvider;
import com.example.bookhub_back.security.auth.CustomEmployeeDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomEmployeeDetailsService customEmployeeDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth->auth
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/files/**").permitAll()
                .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/manager/**").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers("/api/v1/common/**").hasAnyRole( "ADMIN", "MANAGER","STAFF")
                .anyRequest().authenticated()
            )
            .addFilter(new JwtAuthenticationFilter(jwtTokenProvider, customEmployeeDetailsService))
            .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
