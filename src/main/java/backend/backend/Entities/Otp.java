package backend.backend.Entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Timestamp;

@Entity
@Table(name = "otp")
public class Otp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "otp_id")
    private Long otpId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Constructors, getters, and setters
    @Getter
    @Column(name = "otp_code", nullable = false)
    private String otpCode;

    @Column(name = "otp_created_at", nullable = true, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp otpCreatedAt;

    public void setUser(User user){
        this.user = user;
    }

    public void setOtpCode(String otpCode){
        this.otpCode = otpCode;
    }

    /*public Timestamp getOtpCreatedA() {
        return otpCreatedAt;
    }*/

    public void setOtpCreatedA(Timestamp otpCreatedAt) {
        this.otpCreatedAt = otpCreatedAt;
    }
}
