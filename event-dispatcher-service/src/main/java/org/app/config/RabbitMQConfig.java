package org.app.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Alonso
 */
@Configuration
public class RabbitMQConfig {
  
  @Value("${app.rabbitmq.exchange}")
  private String exchangeName;
  
  @Value("${app.rabbitmq.queue.notifications}")
  private String notificationQueue;
  
  @Value("${app.rabbitmq.queue.audit}")
  private String auditQueue;
  
  @Value("${app.rabbitmq.routing-key.user}")
  private String userRoutingKey;
  
  @Value("${app.rabbitmq.routing-key.matricula}")
  private String matriculaRoutingKey;
  
  @Value("${app.rabbitmq.routing-key.audit}")
  private String auditRoutingKey;
  
  @Bean
  public TopicExchange exchange() {
    return new TopicExchange(exchangeName);
  }
  
  @Bean
  public Queue notificationQueue() {
    return QueueBuilder.durable(notificationQueue)
      .withArgument("x-message-ttl", 86400000) // 24 horas
      .build();
  }
  
  @Bean
  public Queue auditQueue() {
    return QueueBuilder.durable(auditQueue)
      .withArgument("x-message-ttl", 604800000) // 7 días
      .build();
  }
  
  // ==================== BINDINGS ====================
  
  @Bean
  public Binding notificationUserBinding() {
    return BindingBuilder
      .bind(notificationQueue())
      .to(exchange())
      .with(userRoutingKey);
  }
  
  @Bean
  public Binding notificationMatriculaBinding() {
    return BindingBuilder
      .bind(notificationQueue())
      .to(exchange())
      .with(matriculaRoutingKey);
  }
  
  @Bean
  public Binding auditBinding() {
    return BindingBuilder
      .bind(auditQueue())
      .to(exchange())
      .with(auditRoutingKey);
  }
  
  @Bean
  public MessageConverter jsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }
  
  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(jsonMessageConverter());
    return rabbitTemplate;
  }
  

}
