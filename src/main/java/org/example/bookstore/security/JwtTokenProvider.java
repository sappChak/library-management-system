package org.example.bookstore.security;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.example.bookstore.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {

  private final String jwtSecret;
  private final int jwtExpirationInMs;

  private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

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

    Map<String, Object> claimsMap = new HashMap<>();
    claimsMap.put("id", user.getId());
    claimsMap.put("username", user.getUsername());
    claimsMap.put("roles", user.getAuthorities());

    logger.debug("User with ID {} and authorities {} authenticated", user.getId(), user.getAuthorities());

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
    } catch (ExpiredJwtException ex) {
      logger.warn("JWT Token is expired: {}", ex.getMessage());
    } catch (SignatureException ex) {
      logger.error("JWT signature does not match: {}", ex.getMessage());
    } catch (MalformedJwtException ex) {
      logger.error("Invalid JWT token: {}", ex.getMessage());
    } catch (UnsupportedJwtException ex) {
      logger.error("Unsupported JWT token: {}", ex.getMessage());
    } catch (IllegalArgumentException ex) {
      logger.error("JWT claims string is empty: {}", ex.getMessage());
    }
    return false;
  }

  public Long getUserIdFromToken(String token) {
    Claims claims = extractAllClaims(token);
    return claims.get("id", Long.class);
  }

  public String getUsernameFromToken(String token) {
    Claims claims = extractAllClaims(token);
    return claims.get("username", String.class);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser()
        .setSigningKey(jwtSecret)
        .parseClaimsJws(token)
        .getBody();
  }
}
