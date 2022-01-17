package objectprotocol;

import domain.Proba;


public class NrParticipantiRequest implements Request {
    private Proba proba;

    public NrParticipantiRequest(Proba proba) {
        this.proba = proba;
    }

    public Proba getProba() {
        return proba;
    }
}

