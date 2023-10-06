package backend.backend.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long userId;

    @Column(name = "user_name") // Specify the column name
    private String userName;

    @Column(name = "user_email") // Specify the column name
    private String userEmail;

    @Getter
    @Column(name = "user_password") // Specify the column name
    public String userPassword;

    @Column(name = "user_phone", nullable = true) // Specify the column name
    public String userPhone;

    public Long getId(){
        return userId;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}

