package com.example.backend.security;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthHelper {
    @Autowired
    JwtUtil jwtUtil;

    /**
     * Verifica se il token è valido e non scaduto.
     * Restituisce true se valido, false altrimenti.
     */
    public boolean verificaAutenticazione(String token) {
        if (token == null || token.isBlank()) {
            return false;
        }
        try {
            Claims claims = jwtUtil.parseToken(token);
            return !jwtUtil.isExpired(token);
        } catch (Exception e) {
            return false;
        }
    }
}
