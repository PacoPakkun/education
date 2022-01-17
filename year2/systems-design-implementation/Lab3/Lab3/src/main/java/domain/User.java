package domain;

import utils.Entity;

public class User extends Entity<Integer> {

    private String username;
    private String password;

    public User(Integer id, String username, String password) {
        super(id);
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
        return super.toString() + " User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
