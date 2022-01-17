package domain;

import java.io.Serializable;

public class Proba implements Serializable {

    private Integer id;
    private Integer distanta;
    private String stil;

    public Proba() {
    }

    public Proba(Integer id, Integer distanta, String stil) {
        this.id = id;
        this.distanta = distanta;
        this.stil = stil;
    }

    public Proba(Integer distanta, String stil) {
        this.distanta = distanta;
        this.stil = stil;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        return "[" + id + "] " + stil + " " + distanta + "m";
    }
}
