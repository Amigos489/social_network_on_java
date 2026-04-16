package social_network.repository.dao;

import org.hibernate.Session;
import social_network.repository.entity.User;

import java.util.List;

public class UserDao {

    private Session session;

    public UserDao(Session session) {
        this.session = session;
    }

    public User findUserById(Integer id) {

        return session.find(User.class, id);
    }

    public List<User> findUsersByName(String name) {

        String hql = "FROM User u WHERE u.name = :name";

        return session.createQuery(hql, User.class).setParameter("name", name).list();
    }

    public List<User> findUsersBySurname(String surname) {

        String hql = "FROM User u WHERE u.surname = :surname";

        return session.createQuery(hql, User.class).setParameter("surname", surname).list();
    }

    public List<User> findUsersByAge(int minAge, int maxAge) {

        String findUserByAgeHql = "FROM User u WHERE u.age BETWEEN :minAge AND :maxAge";

        return session.createQuery(findUserByAgeHql, User.class)
                .setParameter("minAge", minAge)
                .setParameter("maxAge", maxAge).list();
    }

    public List<User> findUsersByGender(boolean gender) {

        String hql = "FROM User u WHERE u.gender = :gender";

        return session.createQuery(hql, User.class)
                .setParameter("gender", gender).list();
    }
}
