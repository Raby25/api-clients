package payetonkawa.api_clients.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import payetonkawa.api_clients.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByEmail(String email);
}
