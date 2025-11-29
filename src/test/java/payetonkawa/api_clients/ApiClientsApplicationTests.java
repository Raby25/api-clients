package payetonkawa.api_clients;

import org.junit.jupiter.api.Test;
import payetonkawa.api_clients.dto.ClientDto;
import payetonkawa.api_clients.model.Client;
import payetonkawa.api_clients.repository.ClientRepository;
import payetonkawa.api_clients.service.ClientService;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApiClientsApplicationTests {

	// 2️⃣ Test rapide des DTOs
	@Test
	void testClientDto() {
		ClientDto dto = new ClientDto(1L, "Nom", "Prenom", "email@example.com", "0123456789");
		assertThat(dto.getId()).isEqualTo(1L);
		assertThat(dto.getNom()).isEqualTo("Nom");
		assertThat(dto.getPrenom()).isEqualTo("Prenom");
		assertThat(dto.getEmail()).isEqualTo("email@example.com");
		assertThat(dto.getTel()).isEqualTo("0123456789");

		dto.setNom("NewNom");
		assertThat(dto.getNom()).isEqualTo("NewNom");
	}

	// 3️⃣ Branches exceptionnelles du service
	@Test
	void testCreateClientThrowsException() {
		ClientRepository repo = mock(ClientRepository.class);
		ClientService service = new ClientService(repo,
				mock(org.springframework.amqp.rabbit.core.RabbitTemplate.class));

		when(repo.save(any())).thenThrow(new RuntimeException("DB error"));

		assertThatThrownBy(() -> service.create(new Client()))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("DB error");
	}

	@Test
	void testUpdateClientNotFound() {
		ClientRepository repo = mock(ClientRepository.class);
		ClientService service = new ClientService(repo,
				mock(org.springframework.amqp.rabbit.core.RabbitTemplate.class));

		when(repo.findById(1L)).thenReturn(java.util.Optional.empty());

		assertThatThrownBy(() -> service.update(1L, new Client()))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("Client not found");
	}

	@Test
	void testDeleteClientNotFound() {
		ClientRepository repo = mock(ClientRepository.class);
		ClientService service = new ClientService(repo,
				mock(org.springframework.amqp.rabbit.core.RabbitTemplate.class));

		when(repo.findById(1L)).thenReturn(java.util.Optional.empty());

		assertThatThrownBy(() -> service.delete(1L))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("Client not found");
	}
}
