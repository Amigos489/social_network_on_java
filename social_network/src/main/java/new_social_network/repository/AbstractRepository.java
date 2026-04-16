package new_social_network.repository;

import org.hibernate.Session;

import java.io.Serializable;

public abstract class AbstractRepository<E, PK extends Serializable> implements GenericRepository<E, PK> {

    private Class<E> entityClass;
    protected Session session;

    public AbstractRepository(Class entityClass, Session session) {

        this.entityClass = entityClass;
        this.session = session;
    }

    @Override
    public E findById(PK id) {

        return session.find(entityClass, id);
    }

    @Override
    public E create(E entity) {

        session.persist(entity);

        session.flush();

        return entity;
    }

    @Override
    public E update(E entity) {

        session.merge(entity);

        session.flush();

        return entity;
    }

    @Override
    public void delete(E entity) {

        session.remove(entity);

        session.flush();
    }
}
