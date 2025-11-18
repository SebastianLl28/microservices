package org.app.authservice.infrastructure.adapter.in.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import org.app.authservice.domain.repository.TokenPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author Alonso
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
  
  private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);
  
  private final TokenPort tokenPort;
  
  public JwtAuthFilter(TokenPort tokenPort) {
    this.tokenPort = tokenPort;
  }
  
  
  @Override
  protected void doFilterInternal(HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain)
    throws ServletException, IOException {
    
    final String authHeader = request.getHeader("Authorization");
    final String token;
    final String username;
    
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }
    
    token = authHeader.substring(7);
    
    try {
      if (tokenPort.validateToken(token)) {
        username = tokenPort.extractUsername(token);
        
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
          
          UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            username,
            null,
            Collections.emptyList()
          );
          
          SecurityContextHolder.getContext().setAuthentication(authToken);
        }
      }
    } catch (Exception e) {
      logger.warn("Invalid JWT Token: " + e.getMessage());
    }
    
    filterChain.doFilter(request, response);
  }
}
