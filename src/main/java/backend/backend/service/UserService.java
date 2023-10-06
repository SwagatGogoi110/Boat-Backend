package backend.backend.service;

import backend.backend.Entities.Otp;
import backend.backend.Entities.User;
import backend.backend.Repository.OtpRepository;
import backend.backend.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User signUpUser(User user){
        return userRepository.save(user);
    }
    public User findById(Long user_id){
        return userRepository.findById(user_id).orElse(null);
    }

    public User updateUser(User user){
        return userRepository.save(user);
    }

    public Optional<User> findByEmailOrUsername(String emailOrUsername) {
        return userRepository.findByUserEmailAndUserName(emailOrUsername, emailOrUsername);
    }

    @Autowired
    private OtpRepository otpRepository;
    @Transactional
    public User registerUser(User user) {
        String otpCode = generateRandomOtp();
        User savedUser = userRepository.save(user);

        Otp otp = new Otp();
        otp.setUser(savedUser);
        otp.setOtpCode(otpCode);
        otp.setOtpCreatedA(new Timestamp(System.currentTimeMillis()));
        otpRepository.save(otp);

        return savedUser;
    }

    private String generateRandomOtp(){
        Random random = new Random();
        int otp = 1000 + random.nextInt(9000);
        return String.valueOf(otp);
    }
}
