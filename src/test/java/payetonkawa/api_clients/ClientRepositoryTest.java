package payetonkawa.api_clients;




import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import payetonkawa.api_clients.model.Client;
import payetonkawa.api_clients.repository.ClientRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    void testSaveAndFindClient() {
        Client client = new Client();
        client.setNom("Diallo");
        client.setPrenom("Nene");
        client.setEmail("nene@example.com");
        client.setTel("0123456789");

        clientRepository.save(client);

        Client found = clientRepository.findById(client.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getNom()).isEqualTo("Diallo");
        assertThat(found.getPrenom()).isEqualTo("Nene");
    }

    @Test
    void testFindByEmail() {
        Client client = new Client();
        client.setNom("Diallo");
        client.setPrenom("Nene");
        client.setEmail("unique@example.com");
        clientRepository.save(client);

        Client found = clientRepository.findByEmail("unique@example.com");
        assertThat(found).isNotNull();
        assertThat(found.getEmail()).isEqualTo("unique@example.com");
    }
}

