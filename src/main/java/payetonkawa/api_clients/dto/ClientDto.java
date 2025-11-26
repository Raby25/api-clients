package payetonkawa.api_clients.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientDto implements Serializable {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String tel;
}
