package objectprotocol;


import domain.User;

public class LoginRequest implements Request {
    private User user;

    public LoginRequest(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
