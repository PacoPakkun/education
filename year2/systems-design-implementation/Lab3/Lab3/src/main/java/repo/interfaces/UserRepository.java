package repo.interfaces;

import domain.User;
import utils.Entity;

public interface UserRepository<ID, E extends Entity<ID>> extends Repository<Integer, User> {
}
