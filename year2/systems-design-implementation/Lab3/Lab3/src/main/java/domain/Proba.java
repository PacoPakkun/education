package domain;

import utils.Entity;

public class Proba extends Entity<Integer> {

    private Integer distanta;
    private String stil;

    public Proba(Integer id, Integer distanta, String stil) {
        super(id);
        this.distanta = distanta;
        this.stil = stil;
    }

    public Integer getDistanta() {
        return distanta;
    }

    public void setDistanta(Integer distanta) {
        this.distanta = distanta;
    }

    public String getStil() {
        return stil;
    }

    public void setStil(String stil) {
        this.stil = stil;
    }

    @Override
    public String toString() {
        return super.toString() + " Proba{" +
                "distanta=" + distanta +
                ", stil='" + stil + '\'' +
                '}';
    }
}
