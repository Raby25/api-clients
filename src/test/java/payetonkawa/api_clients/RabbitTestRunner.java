package payetonkawa.api_clients;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Component
public class RabbitTestRunner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;

    public RabbitTestRunner(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        String testMessage = "Hello RabbitMQ!";
        rabbitTemplate.convertAndSend("clients.exchange", "clients.key", testMessage);
        System.out.println("Message envoyé : " + testMessage);
    }
}