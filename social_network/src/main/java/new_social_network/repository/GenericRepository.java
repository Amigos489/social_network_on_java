package new_social_network.repository;

import java.io.Serializable;

public interface GenericRepository<E, PK extends Serializable> {

    E findById(PK id);

    E create(E entity);

    E update(E entity);

    void delete(E entity);
}
