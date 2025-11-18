package org.app.kafkaservice.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author Alonso
 */
@Component
public class EventLogConsumer {
  
  private static final Logger logger = LoggerFactory.getLogger(EventLogConsumer.class);
  
  private final ObjectMapper objectMapper = new ObjectMapper();
  
  /**
   * Escucha el topic "events-log" y procesa todos los mensajes
   */
  @KafkaListener(
    topics = "${app.kafka.topic.events:events-log}",
    groupId = "${spring.kafka.consumer.group-id}",
    containerFactory = "kafkaListenerContainerFactory"
  )
  public void consumeEvent(
    @Payload String message,
    @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
    @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
    @Header(KafkaHeaders.OFFSET) long offset,
    @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) String key
  ) {
    try {
      logger.info("═══════════════════════════════════════════════════════");
      logger.info("📨 EVENTO RECIBIDO DE KAFKA");
      logger.info("───────────────────────────────────────────────────────");
      logger.info("🏷️  Key: {}", key != null ? key : "null");
      logger.info("📂 Topic: {}", topic);
      logger.info("🔢 Partition: {} | Offset: {}", partition, offset);
      logger.info("───────────────────────────────────────────────────────");
      logger.info("📄 Mensaje:");
      
      // Intentar formatear el JSON para mejor visualización
      try {
        Object json = objectMapper.readValue(message, Object.class);
        String prettyJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        logger.info("\n{}", prettyJson);
      } catch (Exception e) {
        // Si no es JSON válido, imprimir como texto plano
        logger.info("{}", message);
      }
      
      logger.info("═══════════════════════════════════════════════════════\n");
      
    } catch (Exception e) {
      logger.error("❌ Error al procesar evento de Kafka: {}", e.getMessage(), e);
    }
  }
}
