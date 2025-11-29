package payetonkawa.api_clients.service;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import payetonkawa.api_clients.dto.ClientDto;
import payetonkawa.api_clients.model.Client;
import payetonkawa.api_clients.repository.ClientRepository;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;
    private final RabbitTemplate rabbitTemplate;

    public Client create(Client c) {
        Client saved = repository.save(c);
        ClientDto dto = new ClientDto(
                saved.getId(),
                saved.getNom(),
                saved.getPrenom(),
                saved.getEmail(),
                saved.getTel());
        rabbitTemplate.convertAndSend("clients.exchange", (String) "client.created", dto,
                (org.springframework.amqp.core.MessagePostProcessor) message -> message);
        return saved;
    }

    public Client update(Long id, Client c) {
        Client existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        existing.setNom(c.getNom());
        existing.setPrenom(c.getPrenom());
        existing.setEmail(c.getEmail());
        existing.setTel(c.getTel());

        Client updated = repository.save(existing);

        // Créer un DTO pour RabbitMQ
        ClientDto dto = new ClientDto(
                updated.getId(),
                updated.getNom(),
                updated.getPrenom(),
                updated.getEmail(),
                updated.getTel());
        rabbitTemplate.convertAndSend("clients.exchange", (String) "client.updated", dto,
                (org.springframework.amqp.core.MessagePostProcessor) message -> message);
        return updated;
    }

    public void delete(Long id) {
        Client existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        repository.deleteById(id);
        // Créer un DTO pour RabbitMQ
        ClientDto dto = new ClientDto(
                existing.getId(),
                existing.getNom(),
                existing.getPrenom(),
                existing.getEmail(),
                existing.getTel());
        rabbitTemplate.convertAndSend("clients.exchange", (String) "client.deleted", dto,
                (org.springframework.amqp.core.MessagePostProcessor) message -> message);
    }

    public List<Client> all() {
        return repository.findAll();
    }

    public Client get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }

    public Client findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
