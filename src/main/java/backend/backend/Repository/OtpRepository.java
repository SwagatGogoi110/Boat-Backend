package backend.backend.Repository;
import backend.backend.Entities.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Long> {
    @Query("SELECT o FROM Otp o WHERE o.user.userId = :userId")
    Optional<Otp> findByUserId(@Param("userId") Long userId);
}
