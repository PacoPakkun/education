package objectprotocol;


import domain.Inscriere;

public class UpdateResponse implements UpdResponse {
    private Inscriere inscriere;

    public UpdateResponse(Inscriere inscriere) {
        this.inscriere = inscriere;
    }

    public Inscriere getInscriere() {
        return inscriere;
    }
}
