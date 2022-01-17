package repo.intf;

import utils.Entity;

import java.util.List;

public interface Repository<ID, E> {

    E findOne(ID id);

    List<E> findAll();

    E add(E entity) throws Exception;

    E delete(ID id);

    E update(E entity) throws Exception;

}
