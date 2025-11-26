package payetonkawa.api_clients.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MessagePublisher implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final AmqpAdmin amqpAdmin;

    @Value("${rabbitmq.clients.exchange}")
    private String exchange;

    @Value("${rabbitmq.clients.routing-key}")
    private String routingKey;

    public MessagePublisher(RabbitTemplate rabbitTemplate, AmqpAdmin amqpAdmin) {
        this.rabbitTemplate = rabbitTemplate;
        this.amqpAdmin = amqpAdmin;
    }

    @Override
    public void run(String... args) throws Exception {
        // Assure-toi que la queue existe
        while (amqpAdmin.getQueueProperties("clients.queue") == null) {
            System.out.println("Waiting for RabbitMQ queue to be ready...");
            Thread.sleep(1000);
        }

        rabbitTemplate.convertAndSend(exchange, routingKey, "Hello RabbitMQ!");
        System.out.println("Message envoyé !");
    }
}