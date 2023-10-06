package backend.backend.DTO;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String userEmailOrUserName;
    private String password;

    public void setUserEmailOrUserName(String userEmailOrUserName){
        this.userEmailOrUserName = userEmailOrUserName;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
