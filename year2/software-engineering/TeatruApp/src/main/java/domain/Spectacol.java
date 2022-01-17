package domain;

import java.util.*;

public class Spectacol {

    public int id;
    public String nume;
    public String ora;
    public List<Loc> locuri;
    public Manager manager;

    public Spectacol() {
    }

    public Spectacol(int id, String nume, String ora, List<Loc> locuri, Manager manager) {
        this.id = id;
        this.nume = nume;
        this.ora = ora;
        this.locuri = locuri;
        this.manager = manager;
    }

    public Spectacol(int id, String nume, String ora, List<Loc> locuri){
        this.id = id;
        this.nume = nume;
        this.ora = ora;
        this.locuri = locuri;
    }

    public Spectacol(int id, String nume, String ora) {
        this.id = id;
        this.nume = nume;
        this.ora = ora;
    }

    public Spectacol(String nume, String ora) {
        this.nume = nume;
        this.ora = ora;
    }

    public int getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public String getOra() {
        return ora;
    }

    public List<Loc> getLocuri() {
        return locuri;
    }

    public Manager getManager() {
        return manager;
    }

    public void setLocuri(List<Loc> locuri) {
        this.locuri = locuri;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spectacol spectacol = (Spectacol) o;
        return id == spectacol.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nume, ora, locuri, manager);
    }

    @Override
    public String toString() {
        return nume;
    }
}