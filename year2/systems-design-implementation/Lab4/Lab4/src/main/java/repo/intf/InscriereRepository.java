package repo.intf;

import domain.Inscriere;
import domain.Participant;
import domain.Proba;
import utils.Pair;

import java.util.List;

public interface InscriereRepository extends Repository<Pair<Integer, Integer>, Inscriere> {

    List<Participant> getInscrisi(Proba proba);

    List<Proba> getInscrieri(Participant participant);

    Integer nrParticipanti(Proba proba);
}
