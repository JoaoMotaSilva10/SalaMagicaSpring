package br.com.itb.miniprojetospring.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET = "SalaMagicaSecretKeyForJWTTokenGenerationAndValidation2024";
    private final int EXPIRATION = 86400000; // 24 horas

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateToken(String email, String tipoUsuario, Long id) {
        return Jwts.builder()
                .setSubject(email)
                .claim("tipoUsuario", tipoUsuario)
                .claim("id", id)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getEmailFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public String getTipoUsuarioFromToken(String token) {
        return (String) getClaimsFromToken(token).get("tipoUsuario");
    }

    public Long getIdFromToken(String token) {
        return ((Number) getClaimsFromToken(token).get("id")).longValue();
    }

    public boolean isTokenExpired(String token) {
        return getClaimsFromToken(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token) {
        try {
            getClaimsFromToken(token);
            return !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}