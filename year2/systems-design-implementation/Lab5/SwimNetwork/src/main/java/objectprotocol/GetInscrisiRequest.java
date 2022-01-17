package objectprotocol;

import domain.Proba;


public class GetInscrisiRequest implements Request {
    private Proba proba;

    public GetInscrisiRequest(Proba proba) {
        this.proba = proba;
    }

    public Proba getProba() {
        return proba;
    }
}

