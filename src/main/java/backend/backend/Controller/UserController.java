package backend.backend.Controller;

import backend.backend.DTO.LoginRequest;
import backend.backend.service.OtpService;
import backend.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import backend.backend.Entities.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private OtpService otpService;

    @Autowired
    /*private UserController(UserService userService){
        this.userService = userService;
    }*/
    private UserController(UserService userService, OtpService otpService) {
        this.userService = userService;
        this.otpService = otpService;
    }

    @PostMapping("/signup1")
    public ResponseEntity<Long> signUpUser1(@RequestBody User user){
        try{
            User newUser = userService.signUpUser(user);
            Long userId = newUser.getId();
            return ResponseEntity.ok(userId);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*@PostMapping("/getUserId")
    public ResponseEntity<Long> getUserIdByEmailAndName(@RequestBody UserRequest userRequest) {
        String userEmail = userRequest.getUser_email();
        String userName = userRequest.getUser_name();

        Long userId = userService.getUserIdByEmailAndName(userEmail, userName);
        if (userId != null) {
            return ResponseEntity.ok(userId);
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/

    @PostMapping("/signup2")
    public ResponseEntity<String> signUpUser2(@RequestBody Map<String, Object> requestBody) {
        try {
            // Extract the user_id and userPhone from the request body
            Long user_id = ((Number) requestBody.get("user_id")).longValue();
            String user_phone = (String) requestBody.get("userPhone");

            User existingUser = userService.findById(user_id);
            if (existingUser == null) {
                return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
            }

            existingUser.setUserPhone(user_phone);
            userService.updateUser(existingUser); // Use the updateUser method
            User savedUser = userService.registerUser(existingUser); // Use to register for otp
            return new ResponseEntity<>("Phone number added successfully " + savedUser.getId(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add phone number: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {
        boolean isAuthenticated = otpService.authenticateUser(loginRequest.getUserEmailOrUserName(), loginRequest.getPassword());

        if (isAuthenticated) {
            // Fetch the user based on the email/username using the UserService
            Optional<User> userOptional = userService.findByEmailOrUsername(loginRequest.getUserEmailOrUserName());

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                Long userId = user.getId();

                // Return a JSON response containing "Login Successfully" and the userId
                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("message", "Login Successfully");
                responseMap.put("userId", userId);

                return ResponseEntity.ok(responseMap);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
        }
    }




}
