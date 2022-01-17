package objectprotocol;

import domain.Participant;

import java.util.List;


public class NrParticipantiResponse implements Response {
    private int nr;

    public NrParticipantiResponse(int nr) {
        this.nr = nr;
    }

    public int getNr() {
        return nr;
    }
}
