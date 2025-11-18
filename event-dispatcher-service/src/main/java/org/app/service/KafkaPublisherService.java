package org.app.service;

import org.app.dto.GenericEventDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Alonso
 */

@Service
public class KafkaPublisherService {
  
  private static final Logger logger = LoggerFactory.getLogger(KafkaPublisherService.class);
  
  private final KafkaTemplate<String, Object> kafkaTemplate;
  
  @Value("${app.kafka.topic.events:events-log}")
  private String eventsTopic;
  
  public KafkaPublisherService(KafkaTemplate<String, Object> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }
  
  public void publishEvent(GenericEventDTO event) {
    try {
      String key = event.getEventType() != null ? event.getEventType() : "generic";
      
      logger.info("📤 [KAFKA] Enviando evento al topic {} con key {}", eventsTopic, key);
      
      kafkaTemplate
        .send(eventsTopic, key, event)
        .whenComplete((result, ex) -> {
          if (ex != null) {
            logger.error("❌ [KAFKA] Error enviando evento: {}", ex.getMessage(), ex);
          } else if (result != null && result.getRecordMetadata() != null) {
            logger.info("✅ [KAFKA] Evento enviado. Topic={} Partition={} Offset={}",
              result.getRecordMetadata().topic(),
              result.getRecordMetadata().partition(),
              result.getRecordMetadata().offset());
          }
        });
      
    } catch (Exception e) {
      logger.error("❌ [KAFKA] Error inesperado al publicar evento: {}", e.getMessage(), e);
      // Aquí decides si relanzas o no
      // throw new RuntimeException("Error al publicar en Kafka", e);
    }
  }
}
