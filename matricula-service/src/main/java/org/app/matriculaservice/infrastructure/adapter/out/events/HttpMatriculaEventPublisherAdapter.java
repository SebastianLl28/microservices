package org.app.matriculaservice.infrastructure.adapter.out.events;

import java.util.HashMap;
import java.util.Map;
import org.app.matriculaservice.application.port.out.MatriculaEventPublisherPort;
import org.app.matriculaservice.domain.model.Course;
import org.app.matriculaservice.domain.model.Student;
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
public class HttpMatriculaEventPublisherAdapter implements MatriculaEventPublisherPort {
  
  private static final Logger log = LoggerFactory.getLogger(HttpMatriculaEventPublisherAdapter.class);
  
  private final WebClient webClient;
  
  public HttpMatriculaEventPublisherAdapter(
    @Value("${app.event-dispatcher.base-url}") String baseUrl,
    WebClient.Builder webClientBuilder
  ) {
    this.webClient = webClientBuilder
      .baseUrl(baseUrl)
      .build();
  }
  
  @Override
  public void publishStudentEnrolled(Student student, Course course) {
    Map<String, Object> payload = buildPayload(student, course);
    
    Map<String, Object> event = new HashMap<>();
    event.put("eventType", "matricula.enrolled");
    event.put("source", "matricula-service");
    event.put("payload", payload);
    
    sendEvent(event);
  }
  
  @Override
  public void publishStudentUnenrolled(Student student, Course course) {
    Map<String, Object> payload = buildPayload(student, course);
    
    Map<String, Object> event = new HashMap<>();
    event.put("eventType", "matricula.unenrolled");
    event.put("source", "matricula-service");
    event.put("payload", payload);
    
    sendEvent(event);
  }
  
  private Map<String, Object> buildPayload(Student student, Course course) {
    Map<String, Object> payload = new HashMap<>();
    payload.put("studentId", student.getId().getValue());
    payload.put("courseId", course.getId().getValue());
    payload.put("studentEmail", student.getEmail().getValue());
    payload.put("studentName", student.getName() + " " + student.getLastName());
    payload.put("courseName", course.getName());
    return payload;
  }
  
  private void sendEvent(Map<String, Object> event) {
    webClient.post()
      .uri("/api/events/publish/matricula")
      .bodyValue(event)
      .retrieve()
      .bodyToMono(Void.class)
      .doOnError(ex -> log.error("Error enviando evento de matrícula al dispatcher: {}", ex.getMessage()))
      .onErrorResume(ex -> Mono.empty())
      .subscribe();
  }
}
