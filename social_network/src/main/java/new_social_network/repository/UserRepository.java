package new_social_network.repository;

import new_social_network.entity.User;
import org.hibernate.Session;

public class UserRepository extends AbstractRepository<User, Integer> {

    public UserRepository(Session session) {
        super(User.class, session);
    }

    public User findUserById(Integer id) {

        return session.find(User.class, id);
    }

    public User create(String name, String surname, String login, String password) {

        User user = new User(name, surname, login, password);

        super.create(user);

        return user;
    }
}
