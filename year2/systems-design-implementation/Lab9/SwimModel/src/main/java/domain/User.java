package domain;

import utils.Entity;

import java.io.Serializable;

public class User implements Serializable {

    private Integer id;
    private String username;
    private String password;

    public User() {
        this.id = null;
    }

    public User(Integer id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User(String username, String password) {
        this.id = null;
        this.username = username;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
