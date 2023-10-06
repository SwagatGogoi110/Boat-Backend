package backend.backend.service;

import backend.backend.Entities.Otp;
import backend.backend.Entities.User;
import backend.backend.Repository.OtpRepository;
import backend.backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class OtpService {

    public static boolean authenticateUser;
    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private  UserRepository userRepository;

    public boolean verifyOtp(Long userId, String enteredOtp) {
        // Fetch the OTP record for the user from the database
        Optional<Otp> otpOptional = otpRepository.findByUserId(userId);

        if (otpOptional.isPresent()) {
            Otp otp = otpOptional.get();

            // Check if the entered OTP matches the stored OTP
            return enteredOtp.equals(otp.getOtpCode());
        }

        return false;
    }

    public boolean authenticateUser(String userEmailOrUserName, String password){
        Optional <User> userOptional = userRepository.findByUserEmailAndUserName(userEmailOrUserName, userEmailOrUserName);

        if(userOptional.isPresent()){
            User user = userOptional.get();

            System.out.println("User found: " + user.getUserEmail() + " / " + user.getUserName());

            if (user.getUserPassword().equals(password)){
                String newOTPCode = generateNewOTP();

                System.out.println("Authentication successful");
                Optional<Otp> otpOptional = otpRepository.findByUserId(user.getId());
                if(otpOptional.isPresent()){
                    Otp otp = otpOptional.get();
                    otp.setOtpCode(newOTPCode);
                    otpRepository.save(otp);
                }else {
                    System.out.println("Authentication failed: Incorrect password");
                }
                return true;
            }else {
                System.out.println("User not found: " + userEmailOrUserName);
            }
        }
        return false;
    }

    private String generateNewOTP(){
        Random random = new Random();
        int otp = random.nextInt(9000) + 1000;
        return String.valueOf(otp);
    }
}
