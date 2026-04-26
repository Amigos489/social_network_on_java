package social_network.Service;

import social_network.entity.Chat;
import social_network.entity.User;
import social_network.exception.UserNotFoundException;
import social_network.repository.UserRepository;

import java.util.List;

public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String name, String surname,
                     String login, String password) {

        User user = new User(name, surname, login, password);

        return userRepository.create(user);
    }

    public User findUserById(Integer id) {

        User user = userRepository.findById(id);

        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        return user;
    }

    public List<Chat> getAllUserChatsById(Integer id) {
        return userRepository.getAllChatsById(id);
    }

    public boolean isUserBlockedById(Integer id) {
        return userRepository.isBlockedById(id);
    }

    public User findUserByLogin(String login) {

        User user = userRepository.findByLogin(login);

        if (user == null) {
            throw new UserNotFoundException(String.format("User with login = %s not found.", login));
        }

        return user;
    }

    public List<User> findUsersByIds(List<Integer> ids) {

        if (ids.isEmpty()) {
            throw new UserNotFoundException("No users are specified to add.");
        }

        List<User> users = userRepository.findByIds(ids);

        if (users.isEmpty()) {
            throw new UserNotFoundException("Not a single user was found.");
        }

        return users;
    }
}
