package com.mariah.mariah_store.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final JpaUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, JpaUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // Verifica se veio um Bearer token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7); // remove "Bearer "
            
            // Valida token
            if (jwtUtil.isTokenValid(token)) {

                // Extrai o email do token
                String email = jwtUtil.getEmailFromToken(token);

                // Carrega usuário do banco
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                // Cria autenticação
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                // Insere no contexto do Spring
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continua execução
        filterChain.doFilter(request, response);
    }
}
