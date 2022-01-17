package objectprotocol;


import domain.Participant;

public class AddParticipantRequest implements Request{
    private Participant participant;

    public AddParticipantRequest(Participant participant) {
        this.participant = participant;
    }

    public Participant getParticipant() {
        return participant;
    }
}

