package repo.interfaces;

import domain.Proba;
import utils.Entity;

public interface ProbaRepository<ID, E extends Entity<ID>> extends Repository<Integer, Proba> {
}
