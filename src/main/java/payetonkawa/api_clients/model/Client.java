package payetonkawa.api_clients.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;
    @NotBlank(message = "Le prenom est obligatoire")
    private String prenom;

    @Column(unique = true)
    private String email;

    private String tel;

    /**
     * public Long getId() {
     * return id;
     * }
     * 
     * public void setId(Long id) {
     * this.id = id;
     * }
     * 
     * // --- Firstname ---
     * public String getFirstname() {
     * return firstname;
     * }
     * 
     * public void setFirstname(String firstname) {
     * this.firstname = firstname;
     * }
     * 
     * // --- Lastname ---
     * public String getLastname() {
     * return lastname;
     * }
     * 
     * public void setLastname(String lastname) {
     * this.lastname = lastname;
     * }
     * 
     * // --- Email ---
     * public String getEmail() {
     * return email;
     * }
     * 
     * public void setEmail(String email) {
     * this.email = email;
     * }
     * 
     * // --- Phone ---
     * public String getPhone() {
     * return phone;
     * }
     * 
     * public void setPhone(String phone) {
     * this.phone = phone;
     * }
     */

}
