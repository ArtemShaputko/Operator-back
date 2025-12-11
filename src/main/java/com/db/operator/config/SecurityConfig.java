package com.db.operator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {

        var superuser = User.withUsername("superuser")
                .password(encoder.encode("superpass"))
                .roles("SUPERUSER")
                .build();

        return new InMemoryUserDetailsManager(superuser);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        // Просмотр и CRUD по основным таблицам — без логина
                        .requestMatchers("/api/contracts/**",
                                "/api/clients/**",
                                "/api/bills/**",
                                "/api/employees/**",
                                "/api/support-tickets/**",
                                "/api/contract-services/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE,
                                "/api/tariff-plans/**",
                                "/api/services/**",
                                "/api/positions/**",
                                "/api/backup/**").hasRole("SUPERUSER")
                        .requestMatchers(HttpMethod.PUT,
                                "/api/tariff-plans/**",
                                "/api/services/**",
                                "/api/positions/**",
                                "/api/backup/**").hasRole("SUPERUSER")
                        .requestMatchers(HttpMethod.POST,
                                "/api/tariff-plans/**",
                                "/api/services/**",
                                "/api/positions/**",
                                "/api/backup/**").hasRole("SUPERUSER")
                        .requestMatchers(HttpMethod.GET,
                                "/api/backup/**").permitAll()
                        // Просмотр справочников — без логина
                        .requestMatchers(HttpMethod.GET,
                                "/api/tariff-plans/**",
                                "/api/services/**",
                                "/api/positions/**",
                                "/api/query/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/query/**").permitAll()
                        // Остальное — запрещено
                        .anyRequest().denyAll()
                )
                .httpBasic(Customizer.withDefaults())
                .cors(Customizer.withDefaults());

        return http.build();    
    }
}

