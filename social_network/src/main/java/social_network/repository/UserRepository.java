package social_network.repository;

import org.springframework.stereotype.Repository;
import social_network.entity.Chat;
import social_network.entity.User;
import org.hibernate.Session;

import java.util.List;
import java.util.Set;

@Repository
public class UserRepository extends AbstractRepository<User, Integer> {

    public UserRepository(Session session) {
        super(User.class, session);
    }

    public Set<Chat> getAllChatsById(Integer id) {

        User user = findById(id);

        return user.getChats();
    }

    public boolean isBlockedById(Integer id) {

        User user = findById(id);

        return user.isBlocked();
    }

    public User findByLogin(String login) {

        final String hql = "FROM User u WHERE u.login = :login";

        return session.createQuery(hql, User.class)
                .setParameter("login", login)
                .getSingleResultOrNull();
    }

    public List<User> findByIds(List<Integer> ids) {

        final String strListIds = (ids.toString().replaceFirst("\\[", "(")).replaceFirst("]", ")");

        final String hql = "FROM User u WHERE u.id IN " + strListIds;

        return session.createQuery(hql, User.class).list();
    }
}
