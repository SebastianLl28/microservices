package org.app.controller;

import java.util.HashMap;
import java.util.Map;
import org.app.dto.GenericEventDTO;
import org.app.service.EventPublisherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Alonso
 */
@RestController
@RequestMapping("/api/events")
public class EventDispatcherController {
  
  private static final Logger logger = LoggerFactory.getLogger(EventDispatcherController.class);
  
  private final EventPublisherService eventPublisherService;
  
  public EventDispatcherController(EventPublisherService eventPublisherService) {
    this.eventPublisherService = eventPublisherService;
  }
  
  @PostMapping("/publish")
  public ResponseEntity<Map<String, Object>> publishEvent(@RequestBody GenericEventDTO event) {
    try {
      logger.info("Recibida solicitud para publicar evento: {}", event.getEventType());
      
      if (event.getEventType() == null || event.getEventType().isEmpty()) {
        return ResponseEntity
          .badRequest()
          .body(createResponse(false, "EventType es requerido", null));
      }
      
      eventPublisherService.publishEvent(event);
      
      return ResponseEntity
        .ok()
        .body(createResponse(true, "Evento publicado exitosamente", event.getEventType()));
      
    } catch (Exception e) {
      logger.error("Error al procesar evento: {}", e.getMessage(), e);
      return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(createResponse(false, "Error al publicar evento: " + e.getMessage(), null));
    }
  }
  
  @PostMapping("/publish/user")
  public ResponseEntity<Map<String, Object>> publishUserEvent(@RequestBody GenericEventDTO event) {
    try {
      logger.info("Recibido evento de usuario: {}", event.getEventType());
      eventPublisherService.publishUserEvent(event);
      return ResponseEntity.ok(createResponse(true, "Evento de usuario publicado", event.getEventType()));
    } catch (Exception e) {
      logger.error("Error: {}", e.getMessage());
      return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(createResponse(false, e.getMessage(), null));
    }
  }
  
  @PostMapping("/publish/matricula")
  public ResponseEntity<Map<String, Object>> publishMatriculaEvent(@RequestBody GenericEventDTO event) {
    try {
      logger.info("Recibido evento de matrícula: {}", event.getEventType());
      eventPublisherService.publishMatriculaEvent(event);
      return ResponseEntity.ok(createResponse(true, "Evento de matrícula publicado", event.getEventType()));
    } catch (Exception e) {
      logger.error("Error: {}", e.getMessage());
      return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(createResponse(false, e.getMessage(), null));
    }
  }
  
//  auth
//  @PostMapping("/publish/auth")
//  public ResponseEntity<Map<String, Object>> publishAuthEvent(@RequestBody GenericEventDTO event) {
//    try {
//      logger.info("Recibido evento de auth: {}", event.getEventType());
//      eventPublisherService.publishAuthEvent(event);
//      return ResponseEntity.ok(createResponse(true, "Evento de auth publicado", event.getEventType()));
//    } catch (Exception e) {
//      logger.error("Error: {}", e.getMessage());
//      return ResponseEntity
//        .status(HttpStatus.INTERNAL_SERVER_ERROR)
//        .body(createResponse(false, e.getMessage(), null));
//    }
//  }
//
  @GetMapping("/health")
  public ResponseEntity<Map<String, Object>> health() {
    Map<String, Object> response = new HashMap<>();
    response.put("status", "UP");
    response.put("service", "event-dispatcher-service");
    return ResponseEntity.ok(response);
  }
  
  private Map<String, Object> createResponse(boolean success, String message, String eventType) {
    Map<String, Object> response = new HashMap<>();
    response.put("success", success);
    response.put("message", message);
    if (eventType != null) {
      response.put("eventType", eventType);
    }
    return response;
  }
}
