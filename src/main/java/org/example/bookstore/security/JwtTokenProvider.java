package org.example.bookstore.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import org.example.bookstore.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtTokenProvider {

  @Value("${security.jwt.secret}")
  private String jwtSecret;

  @Value("${security.jwt.expiration}")
  private int jwtExpirationInMs;

  public String generateToken(Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    Date now = new Date(System.currentTimeMillis());
    Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

    String userId = Long.toString(user.getId());

    Map<String, Object> claimsMap = new HashMap<>();
    claimsMap.put("id", userId);
    claimsMap.put("username", user.getUsername());

    return Jwts.builder()
        .setSubject(userId)
        .addClaims(claimsMap)
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
    } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException
        | IllegalArgumentException ex) {
      log.error(ex.getMessage());
      return false;
    }
  }

  public Long getUserIdFromToken(String token) {
    Claims claims = Jwts.parser()
        .setSigningKey(jwtSecret)
        .parseClaimsJws(token)
        .getBody();
    String id = (String) claims.get("id");
    return Long.parseLong(id);
  }
}
