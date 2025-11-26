package com.mariah.mariah_store.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Set;

@Component
public class JwtUtil {

    // Lê a chave secreta do application.properties
    @Value("${jwt.secret}")
    private String secret;

    // Tempo de expiração (1 hora)
    private static final long EXPIRATION_TIME = 1000 * 60 * 60;

    // Gera a chave baseada na secret
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // -------- GERAR TOKEN -------- //
    public String generateToken(String email, Set<String> roles) {
        return Jwts.builder()
                .setSubject(email)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // -------- VALIDAR TOKEN -------- //
    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // -------- PEGAR EMAIL -------- //
    public String getEmailFromToken(String token) {
        return getClaims(token).getSubject();
    }

    // -------- PEGAR ROLES -------- //
    public Set<String> getRolesFromToken(String token) {
        return getClaims(token).get("roles", Set.class);
    }

    // -------- PEGAR CLAIMS -------- //
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    
}
