package payetonkawa.api_clients;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import payetonkawa.api_clients.model.Client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ClientValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testNomPrenomNotBlank() {
        Client client = new Client();
        client.setNom("");
        client.setPrenom("");
        client.setEmail("test@example.com");

        Set<ConstraintViolation<Client>> violations = validator.validate(client);

        assertThat(violations).hasSize(2); // nom et prenom
    }
}
