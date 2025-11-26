package payetonkawa.api_clients;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;

@SpringBootTest
class ApiClientsApplicationTests {

	/*
	 * @Autowired
	 * private MockMvc mockMvc;
	 * 
	 * @Test
	 * void testCreateClient() throws Exception {
	 * mockMvc.perform(post("/clients")
	 * .contentType(MediaType.APPLICATION_JSON)
	 * .content("{\"name\":\"test\"}")) // corps JSON de la requête
	 * .andExpect(status().isCreated()); // vérifie que le serveur renvoie 201
	 * }
	 */

}
