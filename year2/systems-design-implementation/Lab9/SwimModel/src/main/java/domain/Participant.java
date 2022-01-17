package domain;

import utils.Entity;

import java.io.Serializable;

public class Participant implements Serializable {

    private Integer id;
    private String nume;
    private Integer varsta;

    public Participant(String nume, Integer varsta) {
        this.id = null;
        this.nume = nume;
        this.varsta = varsta;
    }

    public Participant(Integer id, String nume, Integer varsta) {
        this.id = id;
        this.nume = nume;
        this.varsta = varsta;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Integer getVarsta() {
        return varsta;
    }

    public void setVarsta(Integer varsta) {
        this.varsta = varsta;
    }

    @Override
    public String toString() {
        return super.toString() + " domain.Participant{" +
                "nume='" + nume + '\'' +
                ", varsta=" + varsta +
                '}';
    }

}
