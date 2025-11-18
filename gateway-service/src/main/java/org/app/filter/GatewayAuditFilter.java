package org.app.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Alonso
 */
@Component
public class GatewayAuditFilter implements GlobalFilter, Ordered {
  
  private final WebClient webClient;
  private static final String DISPATCHER_BASE_URL = "http://event-dispatcher-service:8082";
  
  public GatewayAuditFilter(WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder
      .baseUrl(DISPATCHER_BASE_URL)
      .build();
  }
  
  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();
    String path = request.getPath().value();
    HttpMethod method = request.getMethod();
    
    if (path.startsWith("/actuator")) {
      return chain.filter(exchange);
    }
    if (path.startsWith("/auth/validateToken")) {
      return chain.filter(exchange);
    }
    if (HttpMethod.OPTIONS.equals(method)) {
      return chain.filter(exchange);
    }
    
    boolean hasBodyMethod =
      HttpMethod.POST.equals(method) ||
        HttpMethod.PUT.equals(method) ||
        HttpMethod.PATCH.equals(method);
    
    MediaType contentType = request.getHeaders().getContentType();
    boolean isJson = contentType != null && contentType.isCompatibleWith(MediaType.APPLICATION_JSON);
    
    if (!hasBodyMethod || !isJson) {
      Map<String, Object> event = buildEventWithoutBody(request, null);
      sendEventAsync(event);
      return chain.filter(exchange);
    }
    
    return DataBufferUtils.join(request.getBody())
      .flatMap(dataBuffer -> {
        byte[] bytes = new byte[dataBuffer.readableByteCount()];
        dataBuffer.read(bytes);
        DataBufferUtils.release(dataBuffer);
        
        String bodyString = new String(bytes, StandardCharsets.UTF_8);
        
        Map<String, Object> event = buildEventWithoutBody(request, bodyString);
        sendEventAsync(event);
        
        // Reinyectamos el body para que el backend lo reciba
        ServerHttpRequest decorated = new ServerHttpRequestDecorator(request) {
          @Override
          public Flux<DataBuffer> getBody() {
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return Flux.just(buffer);
          }
        };
        
        return chain.filter(exchange.mutate().request(decorated).build());
      });
  }
  
  private Map<String, Object> buildEventWithoutBody(ServerHttpRequest request, String bodyString) {
    String path = request.getPath().value();
    HttpMethod method = request.getMethod();
    
    Map<String, Object> payload = new HashMap<>();
    payload.put("path", path);
    payload.put("method", method != null ? method.name() : "UNKNOWN");
    payload.put("timestamp", Instant.now().toString());
    payload.put("queryParams", request.getQueryParams().toString());
    
    String username = request.getHeaders().getFirst("X-User-Name");
    if (username != null) {
      payload.put("username", username);
    }
    
    if (bodyString != null && !bodyString.isBlank()) {
      try {
        Object json = new ObjectMapper().readValue(bodyString, Object.class);
        payload.put("body", json);
      } catch (Exception ex) {
        payload.put("body", bodyString);
      }
    }
    
    Map<String, Object> event = new HashMap<>();
    event.put("eventType", "gateway.request");
    event.put("sourceSystem", "gateway-service");
    event.put("payload", payload);
    
    return event;
  }
  
  private void sendEventAsync(Map<String, Object> event) {
    webClient.post()
      .uri("/api/events/publish")
      .bodyValue(event)
      .retrieve()
      .bodyToMono(Void.class)
      .onErrorResume(ex -> Mono.empty())
      .subscribe();
  }
  
  @Override
  public int getOrder() {
    return Ordered.LOWEST_PRECEDENCE;
  }
}
