package domain;

import utils.Entity;

import java.io.Serializable;

public class User extends Entity<Integer> implements Serializable {

    private String username;
    private String password;

    public User(Integer id, String username, String password) {
        super(id);
        this.username = username;
        this.password = password;
    }

    public User(String username, String password) {
        super(null);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return super.toString() + " domain.User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
