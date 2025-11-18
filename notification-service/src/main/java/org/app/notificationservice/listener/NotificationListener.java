package org.app.notificationservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.app.notificationservice.dto.CoursePayloadDTO;
import org.app.notificationservice.dto.GenericEventDTO;
import org.app.notificationservice.dto.UserPayloadDTO;
import org.app.notificationservice.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Alonso
 */
@Component
public class NotificationListener {
  
  private static final Logger logger = LoggerFactory.getLogger(NotificationListener.class);
  
  @Autowired
  private ObjectMapper objectMapper;
  
  @Autowired
  private EmailService emailService;
  
  @RabbitListener(queues = "${app.rabbitmq.queue-name}")
  public void handleNotification(GenericEventDTO event) {
    logger.info("Evento recibido de RabbitMQ: {}", event.getEventType());
    
    try {
      String eventType = event.getEventType();
      
      if ("user.registered".equals(eventType)) {
        UserPayloadDTO payload = objectMapper.convertValue(event.getPayload(), UserPayloadDTO.class);
        
        if (payload.getEmail() != null && payload.getUsername() != null) {
          emailService.sendUserCreationEmail(payload.getEmail(), payload.getUsername());
        } else {
          logger.warn("Payload incompleto para user.registered: email o username nulos.");
        }
        
      } else if ("matricula.enrolled".equals(eventType)) {
        CoursePayloadDTO payload = objectMapper.convertValue(event.getPayload(), CoursePayloadDTO.class);
        
        if (payload.getStudentEmail() != null && payload.getStudentName() != null && payload.getCourseName() != null) {
          emailService.sendCourseRegistrationEmail(
            payload.getStudentEmail(),
            payload.getCourseName(),
            payload.getStudentName()
          );
        } else {
          logger.warn("Payload incompleto para matricula.enrolled.");
        }
        
      } else if ("matricula.unenrolled".equals(eventType)) {
        // --- Manejar Desuscripción de Curso ---
        CoursePayloadDTO payload = objectMapper.convertValue(event.getPayload(), CoursePayloadDTO.class);
       
        if (payload.getStudentEmail() != null && payload.getStudentName() != null && payload.getCourseName() != null) {
          emailService.sendCourseUnsubscriptionEmail(
            payload.getStudentEmail(),
            payload.getCourseName(),
            payload.getStudentName()
          );
        } else {
          logger.warn("Payload incompleto para matricula.unenrolled.");
        }
        
      } else {
        logger.warn("Evento recibido pero no manejado: {}", event.getEventType());
      }
      
    } catch (Exception e) {
      logger.error("Error al procesar evento {}: {}", event.getEventType(), e.getMessage(), e);
    }
  }
}
