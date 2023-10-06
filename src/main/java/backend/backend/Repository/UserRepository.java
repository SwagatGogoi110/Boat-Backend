package backend.backend.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import backend.backend.Entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    @Query("SELECT u FROM User u WHERE u.userEmail = :user_email OR u.userName = :user_name")
    Optional<User> findByUserEmailAndUserName(String user_email, String user_name);
}
