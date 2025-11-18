package org.app.service;

import org.app.dto.GenericEventDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Alonso
 */
@Service
public class EventPublisherService {
  
  private static final Logger logger = LoggerFactory.getLogger(EventPublisherService.class);
  
  private final RabbitTemplate rabbitTemplate;
  private final KafkaPublisherService kafkaPublisherService;
  
  @Value("${app.rabbitmq.exchange}")
  private String exchangeName;
  
  @Value("${app.rabbitmq.routing-key.user}")
  private String userRoutingKey;
  
  @Value("${app.rabbitmq.routing-key.matricula}")
  private String matriculaRoutingKey;
  
  @Value("${app.rabbitmq.routing-key.audit}")
  private String auditRoutingKey;
  
  public EventPublisherService(RabbitTemplate rabbitTemplate, KafkaPublisherService kafkaPublisherService) {
    this.rabbitTemplate = rabbitTemplate;
    this.kafkaPublisherService = kafkaPublisherService;
  }
  
  /**
   * Publica un evento genérico
   * Envía a RabbitMQ (notificaciones) y Kafka (logs)
   */
  public void publishEvent(GenericEventDTO event) {
    try {
      event.prepareForPublishing();
      String routingKey = determineRoutingKey(event.getEventType());
      
      logger.info("📤 Publicando evento: {} con routing key: {}", event.getEventType(), routingKey);
      
      // 1. Publicar en RabbitMQ (notificaciones en tiempo real)
      rabbitTemplate.convertAndSend(exchangeName, routingKey, event);
      logger.info("✅ RabbitMQ: Evento publicado - {}", event.getEventType());
      
      // 2. Publicar en Kafka (logs/auditoría)
      kafkaPublisherService.publishEvent(event);
      
      // 3. Publicar también en cola de auditoría de RabbitMQ
      publishToAudit(event);
      
    } catch (Exception e) {
      logger.error("❌ Error al publicar evento {}: {}", event.getEventType(), e.getMessage(), e);
      throw new RuntimeException("Error al publicar evento", e);
    }
  }
  
  /**
   * Determina la routing key basándose en el tipo de evento
   */
  private String determineRoutingKey(String eventType) {
    if (eventType == null) {
      logger.warn("EventType es null, usando routing key por defecto");
      return userRoutingKey;
    }
    
    if (eventType.startsWith("user.")) {
      return userRoutingKey;
    } else if (eventType.startsWith("matricula.")) {
      return matriculaRoutingKey;
    } else {
      logger.warn("EventType desconocido: {}, usando routing key de usuario", eventType);
      return userRoutingKey;
    }
  }
  
  /**
   * Publica en la cola de auditoría
   */
  private void publishToAudit(GenericEventDTO event) {
    try {
      rabbitTemplate.convertAndSend(exchangeName, auditRoutingKey, event);
      logger.debug("📋 Auditoría: Evento guardado - {}", event.getEventType());
    } catch (Exception e) {
      logger.error("❌ Error al enviar evento a auditoría: {}", e.getMessage());
      // No lanzamos excepción para no afectar el flujo principal
    }
  }
  
  /**
   * Publica un evento de usuario
   */
  public void publishUserEvent(GenericEventDTO event) {
    logger.info("👤 Publicando evento de usuario: {}", event.getEventType());
    event.prepareForPublishing();
    rabbitTemplate.convertAndSend(exchangeName, userRoutingKey, event);
    kafkaPublisherService.publishEvent(event);
    publishToAudit(event);
  }
  
  /**
   * Publica un evento de matrícula
   */
  public void publishMatriculaEvent(GenericEventDTO event) {
    logger.info("🎓 Publicando evento de matrícula: {}", event.getEventType());
    event.prepareForPublishing();
    rabbitTemplate.convertAndSend(exchangeName, matriculaRoutingKey, event);
    kafkaPublisherService.publishEvent(event);
    publishToAudit(event);
  }
}
