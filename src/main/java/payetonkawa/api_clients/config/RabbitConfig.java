package payetonkawa.api_clients.config;

import org.springframework.amqp.core.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${rabbitmq.clients.queue}")
    private String queueName;

    @Value("${rabbitmq.clients.exchange}")
    private String exchangeName;

    @Value("${rabbitmq.clients.routing-key}")
    private String routingKey;

    @Bean
    public Queue clientsQueue() {
        return new Queue(queueName, false);
    }

    @Bean
    public TopicExchange clientsExchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Binding binding(Queue clientsQueue, TopicExchange clientsExchange) {
        return BindingBuilder.bind(clientsQueue).to(clientsExchange).with(routingKey);
    }
}