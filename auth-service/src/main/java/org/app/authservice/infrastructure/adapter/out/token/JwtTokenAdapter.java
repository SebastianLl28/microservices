package org.app.authservice.infrastructure.adapter.out.token;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.app.authservice.domain.repository.TokenPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Alonso
 */
@Component
public class JwtTokenAdapter implements TokenPort {
  
  private static final Logger logger = LoggerFactory.getLogger(JwtTokenAdapter.class);
  
  @Value("${jwt.secret}")
  private String SECRET_KEY;
  
  @Value("${jwt.expiration}")
  private String EXPIRATION_TIME_MS;
  
  private SecretKey getSigningKey() {
    return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
  }
  
  @Override
  public String generateToken(String username) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + Long.parseLong(EXPIRATION_TIME_MS));
    
    return Jwts.builder()
      .subject(username)
      .issuedAt(now)
      .expiration(expiryDate)
      .signWith(getSigningKey())
      .compact();
  }
  
  @Override
  public String extractUsername(String token) {
    try {
      return Jwts.parser()
        .verifyWith(getSigningKey())
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .getSubject();
    } catch (Exception ex) {
      logger.error("Error extracting username from token: {}", ex.getMessage());
      return null;
    }
  }
  
  @Override
  public boolean validateToken(String token) {
    try {
      Jwts.parser()
        .verifyWith(getSigningKey())
        .build()
        .parseSignedClaims(token);
      return true;
    } catch (MalformedJwtException ex) {
      logger.error("Invalid JWT token: {}", ex.getMessage());
    } catch (ExpiredJwtException ex) {
      logger.error("Expired JWT token: {}", ex.getMessage());
    } catch (UnsupportedJwtException ex) {
      logger.error("Unsupported JWT token: {}", ex.getMessage());
    } catch (IllegalArgumentException ex) {
      logger.error("JWT claims string is empty: {}", ex.getMessage());
    }
    return false;
  }
}
