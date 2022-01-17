package domain;

import java.util.*;

public class Manager {

    public int id;
    public String nume;
    public String password;
    public Set<Spectacol> spectacole = new HashSet<>();

    public String getNume() {
        return nume;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Spectacol> getSpectacole() {
        return spectacole;
    }

    public void setSpectacole(Set<Spectacol> spectacole) {
        this.spectacole = spectacole;
    }

    public Manager() {
    }

    public Manager(int id, String nume, String password) {
        this.id = id;
        this.nume = nume;
        this.password = password;
    }

}