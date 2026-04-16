package new_social_network.Service;

import new_social_network.entity.User;
import new_social_network.exception.UserNotFoundException;
import new_social_network.repository.UserRepository;

public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(String name, String surname,
                     String login, String password) {
        return userRepository.create(name, surname, login, password);
    }

    public User findUserById(Integer id) {

        User user = userRepository.findUserById(id);

        if (user == null) {
            throw new UserNotFoundException(id);
        }

        return user;
    }
}
