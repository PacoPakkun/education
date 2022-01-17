package domain;

import java.io.Serializable;

public class Loc implements Serializable {

    public int nr;
    public String pozitie;
    public float pret;
    public Status stare;
    public Spectacol spectacol;
    public Spectator spectator;

    public Loc() {
    }

    public Loc(int nr, String pozitie, float pret, Spectacol spectacol) {
        this.nr = nr;
        this.pozitie = pozitie;
        this.pret = pret;
        this.stare = Status.liber;
        this.spectacol = spectacol;
        this.spectator = null;
    }

    public Loc(int nr, String pozitie, float pret) {
        this.nr = nr;
        this.pozitie = pozitie;
        this.pret = pret;
        this.stare = Status.liber;
        this.spectacol = null;
        this.spectator = null;
    }

    public int getNr() {
        return nr;
    }

    public String getPozitie() {
        return pozitie;
    }

    public float getPret() {
        return pret;
    }

    public Status getStare() {
        return stare;
    }

    public Spectacol getSpectacol() {
        return spectacol;
    }

    public Spectator getSpectator() {
        return spectator;
    }

    public void setStare(Status stare) {
        this.stare = stare;
    }

    public void setSpectator(Spectator spectator) {
        this.spectator = spectator;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public void setPozitie(String pozitie) {
        this.pozitie = pozitie;
    }

    public void setPret(float pret) {
        this.pret = pret;
    }

    public void setSpectacol(Spectacol spectacol) {
        this.spectacol = spectacol;
    }

    @Override
    public String toString() {
        return "Bilet #" + this.nr + "  " + this.pret + " RON";
    }
}