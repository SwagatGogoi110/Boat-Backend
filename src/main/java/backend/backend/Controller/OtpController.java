package backend.backend.Controller;

import backend.backend.Repository.OtpRepository;
import backend.backend.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users/otp")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @PostMapping("/verify")
    public ResponseEntity<HttpStatus> verifyOtp(@RequestBody Map<String, Object> requestBody) {
        Long userId = Long.parseLong((String) requestBody.get("userId"));
        String enteredOtp = (String) requestBody.get("enteredOtp");
        boolean isOtpValid = otpService.verifyOtp(userId, enteredOtp);

        if (isOtpValid) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
