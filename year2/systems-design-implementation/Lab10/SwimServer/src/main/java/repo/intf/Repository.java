package repo.intf;

import utils.Entity;

import java.util.List;

public interface Repository<ID, E> {

    E findOne(ID id) throws Exception;

    List<E> findAll();

    E add(E entity);

    E delete(ID id) throws Exception;

    E update(E entity) throws Exception;

}
