package domain;

import utils.Entity;

public class Participant extends Entity<Integer> {

    private String nume;
    private Integer varsta;

    public Participant(Integer id, String nume, Integer varsta) {
        super(id);
        this.nume = nume;
        this.varsta = varsta;
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
        return super.toString() + " Participant{" +
                "nume='" + nume + '\'' +
                ", varsta=" + varsta +
                '}';
    }

}
