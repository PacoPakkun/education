package domain;

import utils.Entity;
import utils.Pair;

import java.io.Serializable;

public class Inscriere extends Entity<Pair<Integer, Integer>> implements Serializable {

    private Participant participant;
    private Proba proba;

    public Inscriere(Integer id_participant, Integer id_proba, Participant participant, Proba proba) {
        super(new Pair<>(id_participant, id_proba));
        this.participant = participant;
        this.proba = proba;
    }

    public Inscriere(Integer id_participant, Integer id_proba) {
        super(new Pair<>(id_participant, id_proba));
        this.participant = null;
        this.proba = null;
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
