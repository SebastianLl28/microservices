package org.app.notificationservice.config;

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
  
  @Value("${app.rabbitmq.exchange-name}")
  private String exchangeName;
  
  @Value("${app.rabbitmq.queue-name}")
  private String queueName;
  
  @Value("${app.rabbitmq.binding-key}")
  private String bindingKey;
  
  @Bean
  public TopicExchange topicExchange() {
    return new TopicExchange(exchangeName);
  }
  
  @Bean
  public Queue notificationQueue() {
    return QueueBuilder.durable(queueName)
      .withArgument("x-message-ttl", 86400000)
      .build();
  }
  
  @Bean
  public Binding binding(Queue notificationQueue, TopicExchange topicExchange) {
    return BindingBuilder
      .bind(notificationQueue)
      .to(topicExchange)
      .with(bindingKey);
  }
  
  @Bean
  public MessageConverter jsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }
  
  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate template = new RabbitTemplate(connectionFactory);
    template.setMessageConverter(jsonMessageConverter());
    return template;
  }
}
