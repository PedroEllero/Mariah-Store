package com.mariah.mariah_store.config;

import com.mariah.mariah_store.auth.JwtAuthenticationFilter;
import com.mariah.mariah_store.auth.JpaUserDetailsService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;
    private final JpaUserDetailsService userDetailsService;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter, JpaUserDetailsService userDetailsService) {
        this.jwtFilter = jwtFilter;
        this.userDetailsService = userDetailsService;
    }

    // Password encoder (BCrypt)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager (Spring Security 6)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Configuração principal de segurança
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable()) // API → CSRF desligado

            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .authorizeHttpRequests(auth -> auth
                // LIBERAR ARQUIVOS WEB ESTÁTICOS
                .requestMatchers(
                    "/", 
                    "/index.html",
                    "/*.html",
                    "/*.css",
                    "/*.js",
                    "/static/**",
                    "/css/**",
                    "/js/**",
                    "/imagens/**"
                ).permitAll()

                // LIBERAR ROTAS DA API
                .requestMatchers(HttpMethod.POST, "/clientes").permitAll()
                .requestMatchers("/clientes/login").permitAll()
                .requestMatchers("/produtos/**").permitAll()

                // ROTAS ADMIN
                .requestMatchers("/admin/**").hasRole("ADMIN")

                // O RESTO PRECISA DE TOKEN
                .anyRequest().authenticated()
            )



            // Adiciona nosso filtro JWT antes do filtro padrão de login
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
