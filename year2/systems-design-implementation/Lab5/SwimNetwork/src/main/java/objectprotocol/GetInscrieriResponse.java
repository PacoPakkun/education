package objectprotocol;

import domain.Proba;

import java.util.List;


public class GetInscrieriResponse implements Response {
    private List<Proba> probe;

    public GetInscrieriResponse(List<Proba> probe) {
        this.probe = probe;
    }

    public List<Proba> getProbe() {
        return probe;
    }
}
