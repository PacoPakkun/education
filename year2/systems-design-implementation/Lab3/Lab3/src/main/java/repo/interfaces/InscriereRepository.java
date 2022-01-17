package repo.interfaces;

import domain.Inscriere;
import domain.Participant;
import domain.Proba;
import utils.Entity;
import utils.Pair;

import java.sql.SQLException;

public interface InscriereRepository<ID, E extends Entity<ID>> extends Repository<Pair<Integer, Integer>, Inscriere> {

    Iterable<Participant> getInscrisi(Proba proba) throws SQLException;

    Integer nrParticipanti(Proba proba) throws SQLException;
}
