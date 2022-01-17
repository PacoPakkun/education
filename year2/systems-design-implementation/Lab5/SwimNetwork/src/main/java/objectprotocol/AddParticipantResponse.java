package objectprotocol;


import domain.Participant;

public class AddParticipantResponse implements Response {
    private Participant p;

    public AddParticipantResponse(Participant p) {
        this.p = p;
    }

    public Participant getParticipant() {
        return p;
    }
}
