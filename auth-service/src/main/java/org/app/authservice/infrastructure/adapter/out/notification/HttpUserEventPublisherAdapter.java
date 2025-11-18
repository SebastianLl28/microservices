package org.app.authservice.infrastructure.adapter.out.notification;

import java.util.HashMap;
import java.util.Map;
import org.app.authservice.application.port.out.UserEventPublisherPort;
import org.app.authservice.domain.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author Alonso
 */
@Component
public class HttpUserEventPublisherAdapter implements UserEventPublisherPort {
  
  private static final Logger log = LoggerFactory.getLogger(HttpUserEventPublisherAdapter.class);
  
  private final WebClient webClient;
  
  public HttpUserEventPublisherAdapter(
    @Value("${event.dispatcher.url}") String baseUrl,
    WebClient.Builder webClientBuilder
  ) {
    this.webClient = webClientBuilder
      .baseUrl(baseUrl)
      .build();
  }
  
  @Override
  public void publishUserRegistered(User user) {
    Map<String, Object> payload = buildPayload(user);
    
    Map<String, Object> event = new HashMap<>();
    event.put("eventType", "user.registered");
    event.put("source", "auth-service");
    event.put("payload", payload);
    
    sendEvent(event);
  }
  
  private Map<String, Object> buildPayload(User user) {
    Map<String, Object> payload = new HashMap<>();
    payload.put("username", user.getUsername());
    payload.put("email", user.getEmail());
    payload.put("fullName", user.getFullName());
    return payload;
  }
  
  private void sendEvent(Map<String, Object> event) {
    webClient.post()
      .uri("/api/events/publish/user")
      .bodyValue(event)
      .retrieve()
      .bodyToMono(Void.class)
      .doOnError(ex ->
        log.error("Error enviando evento user.registered al dispatcher: {}", ex.getMessage()))
      .onErrorResume(ex -> Mono.empty())
      .subscribe();
  }
}
