package com.example.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {
    private static final String SECRET = "thisIsASecretKeyThatIsLongEnoughForHmacSha256";
    private static final long EXPIRATION_MS = 3600_000; // 1h

    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

    // Generazione token con informazioni extra
    public String generateToken(Long id, String email, String role) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + EXPIRATION_MS);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .addClaims(Map.of(
                        "id", id,
                        "email", email,
                        "role", role
                ))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Estrae i claims
    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Recupera dati singoli
    public Long getId(String token) { return parseToken(token).get("id", Long.class); }
    public String getEmail(String token) { return parseToken(token).get("email", String.class); }
    public String getRole(String token) { return parseToken(token).get("role", String.class); }

    public boolean isExpired(String token) {
        return parseToken(token).getExpiration().before(new Date());
    }
}
