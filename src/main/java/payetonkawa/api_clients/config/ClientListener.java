package payetonkawa.api_clients.config;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ClientListener {

    @RabbitListener(queues = "clients.queue")
    public void receive(String message) {
        System.out.println("Message reçu : " + message);
    }
}