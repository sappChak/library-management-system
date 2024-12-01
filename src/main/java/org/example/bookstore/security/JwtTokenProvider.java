package org.example.bookstore.security;

import io.jsonwebtoken.*;
import org.example.bookstore.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    private final String jwtSecret;
    private final int jwtExpirationInMs;

    public JwtTokenProvider(
            @Value("${security.jwt.secret}") String jwtSecret,
            @Value("${security.jwt.expiration}") int jwtExpirationInMs) {
        this.jwtSecret = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
        this.jwtExpirationInMs = jwtExpirationInMs;
    }

    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        var claimsMap = createClaimsMap(user);

        logger.debug("Generating JWT token for user: {}", user.getUsername());

        return Jwts.builder()
                .setClaims(claimsMap)
                .setSubject(Long.toString(user.getId()))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            logger.error("JWT validation failed: {}", e.getMessage());
            throw new JwtException("JWT validation failed");
        }

    }

    public Long getUserIdFromToken(String token) {
        return extractAllClaims(token).get("id", Long.class);
    }

    public String getUsernameFromToken(String token) {
        return extractAllClaims(token).get("username", String.class);
    }

    private Map<String, Object> createClaimsMap(User user) {
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("id", user.getId());
        claimsMap.put("username", user.getUsername());
        claimsMap.put("roles", user.getAuthorities());
        return claimsMap;
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
    }
}
