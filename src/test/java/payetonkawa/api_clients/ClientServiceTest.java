package payetonkawa.api_clients;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import payetonkawa.api_clients.dto.ClientDto;
import payetonkawa.api_clients.model.Client;
import payetonkawa.api_clients.repository.ClientRepository;
import payetonkawa.api_clients.service.ClientService;

import java.util.Optional;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateClient() {
        Client client = new Client(null, "Diallo", "Nene", "new@example.com", "0123456789");
        when(clientRepository.save(client)).thenReturn(client);

        Client created = clientService.create(client);

        assertThat(created).isNotNull();
        assertThat(created.getEmail()).isEqualTo("new@example.com");

        verify(clientRepository).save(client);
        verify(rabbitTemplate).convertAndSend(
                anyString(),
                anyString(),
                any(ClientDto.class),
                any(MessagePostProcessor.class));
    }

    @Test
    void testUpdateClient() {
        Client existing = new Client(1L, "Old", "Name", "old@example.com", "000");
        Client updatedData = new Client(null, "Diallo", "Nene", "new@example.com", "0123456789");

        when(clientRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(clientRepository.save(any(Client.class))).thenReturn(existing);

        Client updated = clientService.update(1L, updatedData);

        assertThat(updated.getNom()).isEqualTo("Diallo");
        assertThat(updated.getEmail()).isEqualTo("new@example.com");

        verify(clientRepository).save(existing);
        verify(rabbitTemplate).convertAndSend(
                anyString(),
                anyString(),
                any(ClientDto.class),
                any(MessagePostProcessor.class));
    }

    @Test
    void testUpdateClientNotFound() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clientService.update(1L, new Client()))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Client not found");
    }

    @Test
    void testDeleteClient() {
        Client existing = new Client(1L, "Diallo", "Nene", "nene@example.com", "0123456789");
        when(clientRepository.findById(1L)).thenReturn(Optional.of(existing));

        clientService.delete(1L);

        verify(clientRepository).deleteById(1L);
        verify(rabbitTemplate).convertAndSend(
                anyString(),
                anyString(),
                any(ClientDto.class),
                any(MessagePostProcessor.class));
    }

    @Test
    void testDeleteClientNotFound() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clientService.delete(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Client not found");
    }

    @Test
    void testGetClient() {
        Client client = new Client(1L, "Diallo", "Nene", "nene@example.com", "0123456789");
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        Client result = clientService.get(1L);

        assertThat(result).isEqualTo(client);
    }

    @Test
    void testGetClientNotFound() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clientService.get(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Client not found");
    }

    @Test
    void testAllClients() {
        List<Client> clients = List.of(
                new Client(1L, "Diallo", "Nene", "n1@example.com", "0123"),
                new Client(2L, "Test", "User", "n2@example.com", "0456"));
        when(clientRepository.findAll()).thenReturn(clients);

        List<Client> result = clientService.all();

        assertThat(result).hasSize(2);
    }

    @Test
    void testFindByEmail() {
        Client client = new Client(1L, "Diallo", "Nene", "nene@example.com", "0123456789");
        when(clientRepository.findByEmail("nene@example.com")).thenReturn(client);

        Client result = clientService.findByEmail("nene@example.com");

        assertThat(result).isEqualTo(client);
    }
}