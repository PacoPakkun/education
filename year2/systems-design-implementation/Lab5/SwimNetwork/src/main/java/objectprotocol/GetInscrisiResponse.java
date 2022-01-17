package objectprotocol;

import domain.Participant;

import java.util.List;


public class GetInscrisiResponse implements Response {
    private List<Participant> participants;

    public GetInscrisiResponse(List<Participant> participants) {
        this.participants = participants;
    }

    public List<Participant> getParticipants() {
        return participants;
    }
}
