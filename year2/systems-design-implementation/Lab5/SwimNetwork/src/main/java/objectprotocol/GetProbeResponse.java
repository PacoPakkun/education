package objectprotocol;

import domain.Proba;

import java.util.List;


public class GetProbeResponse implements Response {
    private List<Proba> probe;

    public GetProbeResponse(List<Proba> probe) {
        this.probe = probe;
    }

    public List<Proba> getProbe() {
        return probe;
    }
}
