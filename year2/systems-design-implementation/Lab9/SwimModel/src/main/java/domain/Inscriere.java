package domain;

import utils.Entity;
import utils.Pair;

import java.io.Serializable;

public class Inscriere implements Serializable {

    private Integer id_participant;
    private Integer id_proba;
    private Participant participant;
    private Proba proba;

    public Inscriere(Integer id_participant, Integer id_proba, Participant participant, Proba proba) {
        this.id_participant = id_participant;
        this.id_proba = id_proba;
        this.participant = participant;
        this.proba = proba;
    }

    public Inscriere(Integer id_participant, Integer id_proba) {
        this.id_participant = id_participant;
        this.id_proba = id_proba;
        this.participant = null;
        this.proba = null;
    }

    public Integer getId_participant() {
        return id_participant;
    }

    public void setId_participant(Integer id_participant) {
        this.id_participant = id_participant;
    }

    public Integer getId_proba() {
        return id_proba;
    }

    public void setId_proba(Integer id_proba) {
        this.id_proba = id_proba;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public Proba getProba() {
        return proba;
    }

    public void setProba(Proba proba) {
        this.proba = proba;
    }

    @Override
    public String toString() {
        return "domain.Inscriere{" +
                "participant=" + participant +
                ", proba=" + proba +
                '}';
    }
}
