package payetonkawa.api_clients;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import payetonkawa.api_clients.controller.ClientController;
import payetonkawa.api_clients.model.Client;
import payetonkawa.api_clients.service.ClientService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ClientControllerTest {

        private MockMvc mockMvc;

        @Mock
        private ClientService clientService;

        @InjectMocks
        private ClientController clientController;

        @BeforeEach
        void setUp() {
                MockitoAnnotations.openMocks(this);
                mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
        }

        @Test
        void testGetClientById() throws Exception {
                Client client = new Client(1L, "Diallo", "Nene", "nene@example.com", "0123456789");
                when(clientService.get(1L)).thenReturn(client);

                mockMvc.perform(get("/clients/1"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.email").value("nene@example.com"));
        }

        @Test
        void testGetAllClients() throws Exception {
                List<Client> clients = List.of(
                                new Client(1L, "Diallo", "Nene", "n1@example.com", "0123"),
                                new Client(2L, "Test", "User", "n2@example.com", "0456"));
                when(clientService.all()).thenReturn(clients);

                mockMvc.perform(get("/clients"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.length()").value(2));
        }

        @Test
        void testCreateClient() throws Exception {
                Client client = new Client(null, "Diallo", "Nene", "new@example.com", "0123456789");
                when(clientService.create(any(Client.class))).thenReturn(client);

                mockMvc.perform(post("/clients")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                                "{\"nom\":\"Diallo\",\"prenom\":\"Nene\",\"email\":\"new@example.com\",\"tel\":\"0123456789\"}"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.email").value("new@example.com"));
        }

        @Test
        void testUpdateClient() throws Exception {
                Client client = new Client(1L, "Diallo", "Nene", "updated@example.com", "0123456789");
                when(clientService.update(eq(1L), any(Client.class))).thenReturn(client);

                mockMvc.perform(put("/clients/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                                "{\"nom\":\"Diallo\",\"prenom\":\"Nene\",\"email\":\"updated@example.com\",\"tel\":\"0123456789\"}"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.email").value("updated@example.com"));
        }

        @Test
        void testDeleteClient() throws Exception {
                doNothing().when(clientService).delete(1L);

                mockMvc.perform(delete("/clients/1"))
                                .andExpect(status().isOk());

                verify(clientService).delete(1L);
        }
}
