package objectprotocol;


import domain.Inscriere;

public class AddInscriereRequest implements Request{
    private Inscriere inscriere;

    public AddInscriereRequest(Inscriere inscriere) {
        this.inscriere = inscriere;
    }

    public Inscriere getInscriere() {
        return inscriere;
    }
}

