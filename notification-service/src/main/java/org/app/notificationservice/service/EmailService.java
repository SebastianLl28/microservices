package org.app.notificationservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author Alonso
 */
@Service
public class EmailService {
  
  private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
  
  @Autowired
  private JavaMailSender mailSender;
  
  @Value("${spring.mail.username}")
  private String fromEmail;
  
  private void sendEmail(String toEmail, String subject, String body) {
    try {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setFrom(fromEmail);
      message.setTo(toEmail);
      message.setSubject(subject);
      message.setText(body);
      mailSender.send(message);
      logger.info("Email sent to {}", toEmail);
    } catch (Exception e) {
      logger.error("Failed to send email to {}: {}", toEmail, e.getMessage());
    }
  }
  
  public void sendCourseRegistrationEmail(String toEmail, String courseName, String studentName) {
    String subject = "Confirmación de inscripción al curso: " + courseName;
    String body = "Hola " + studentName + ",\n\n" +
      "Te has inscrito exitosamente en el curso: " + courseName + ".\n\n" +
      "¡Gracias por unirte a nosotros!\n\n" +
      "Saludos,\n" +
      "El equipo de la plataforma educativa.";
    sendEmail(toEmail, subject, body);
  }
  
  public void sendUserCreationEmail(String toEmail, String username) {
    String subject = "Bienvenido a la plataforma educativa, " + username + "!";
    String body = "Hola " + username + ",\n\n" +
      "Tu cuenta ha sido creada exitosamente.\n\n" +
      "¡Gracias por unirte a nosotros!\n\n" +
      "Saludos,\n" +
      "El equipo de la plataforma educativa.";
    sendEmail(toEmail, subject, body);
  }
  
  public void sendCourseUnsubscriptionEmail(String toEmail, String courseName, String studentName) {
    String subject = "Confirmación de desuscripción del curso: " + courseName;
    String body = "Hola " + studentName + ",\n\n" +
      "Te has desuscrito exitosamente del curso: " + courseName + ".\n\n" +
      "Lamentamos verte ir.\n\n" +
      "Saludos,\n" +
      "El equipo de la plataforma educativa.";
    sendEmail(toEmail, subject, body);
  }
}
