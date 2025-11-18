package org.app.filter;

import org.app.config.RouterValidator;
import org.app.dto.ValidationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

/**
 * @author Alonso
 */
@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
  
  @Autowired
  private RouterValidator routerValidator;
  
  @Autowired
  private WebClient.Builder webClientBuilder;
  
  private final String authServiceUrl = "http://auth-service:8080";
  
  public AuthenticationFilter() {
    super(Config.class);
  }
  
  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      System.out.println("Pasando por el gateway");
      System.out.println(exchange.getRequest().getPath());
      if (routerValidator.isSecured.test(exchange.getRequest())) {
        
        if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
          return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing authorization header"));
        }
        
        String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
          return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid authorization header"));
        
        }
        String token = authHeader.substring(7);
        
        return webClientBuilder.build()
          .get()
          .uri(authServiceUrl + "/auth/validateToken", uriBuilder -> uriBuilder.queryParam("token", token).build())
          .retrieve()
          .bodyToMono(ValidationResponse.class)
          .flatMap(response -> {
            if (response.isValid()) {
              exchange.getRequest().mutate()
                .header("X-User-Name", response.getUsername());
              return chain.filter(exchange);
            } else {
              return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token"));
            }
          })
          .onErrorResume(e -> {
            return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token validation failed", e));
          });
      }
      
      return chain.filter(exchange);
    };
  }
  
  public static class Config {
  }
  
}
