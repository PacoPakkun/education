package objectprotocol;

import domain.Participant;


public class GetInscrieriRequest implements Request {
    private Participant participant;

    public GetInscrieriRequest(Participant user) {
        this.participant = user;
    }

    public Participant getParticipant() {
        return participant;
    }
}

