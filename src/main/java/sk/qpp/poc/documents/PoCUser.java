package sk.qpp.poc.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PoCUser {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, length = 256, nullable = false, insertable = true) //TODO , updatable = false)
    @Size(min = 3, max = 256, message = "loginName of PoCUser should have at least 3 characters and not exceed 256 characters!")
    private String loginName;

    @NotNull
    @Email
    @Size(max = 100)
    @Column(unique = true)
    private String email;

    public PoCUser(String loginName, String email) {
        this.loginName = loginName;
        this.email = email;
    }
}
