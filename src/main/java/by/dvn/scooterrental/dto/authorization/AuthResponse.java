package by.dvn.scooterrental.dto.authorization;

public class AuthResponse {
    private String userName;
    private String token;

    public AuthResponse(String userName, String token) {
        this.userName = userName;
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public String getToken() {
        return token;
    }

}
