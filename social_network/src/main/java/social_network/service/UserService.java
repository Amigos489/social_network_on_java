package social_network.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import social_network.entity.Chat;
import social_network.entity.User;
import social_network.enums.Role;
import social_network.exception.EmailAlreadyBusyException;
import social_network.exception.NotFoundException;
import social_network.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private static Logger logger = LogManager.getLogger(UserService.class);

    private PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public User createUser(String name, String surname,
                           String login, String password) {

        logger.info("create user");

        String encodedPassword = passwordEncoder.encode(password);

        User user = new User(name, surname, login, encodedPassword, Role.USER);

        return userRepository.create(user);
    }

    public User findUserById(Integer id) {

        logger.info((String.format("find user with id = %d.", id)));
        User user = userRepository.findById(id);

        if (user == null) {
            logger.error((String.format("user with id = %d not found", id)));
            throw new NotFoundException(String.format("User with id = %d not found.", id));
        }

        logger.info((String.format("user with id = %d found.", id)));
        return user;
    }

    public void checkingIfEmailOccupied(String login) {

        logger.info((String.format("find user with login = %s.", login)));

        User user = userRepository.findByLogin(login);

        if (user != null) {
            logger.error((String.format("Email %s already busy.", login)));
            throw new EmailAlreadyBusyException(String.format("Email %s already busy.", login));
        }

        logger.info((String.format("email %s is available.", login)));
    }

    public Set<User> findUsersByIds(List<Integer> ids) {

        logger.info((String.format("find users with ids = %s.", ids.toString())));

        if (ids.isEmpty()) {
            logger.error("no users are specified to add.");
            throw new NotFoundException("no users are specified to add.");
        }

        Set<User> users = new HashSet<>(userRepository.findByIds(ids));

        if (users.isEmpty()) {
            logger.error("not a single user was found.");
            throw new NotFoundException("not a single user was found.");
        }

        logger.info("users with ids with ids = %s found.", ids.toString());
        return users;
    }

    public Set<Chat> findAllUserChatsById(Integer id) {

        logger.info((String.format("find all chats of a user with id = %d.", id)));
        return userRepository.getAllChatsById(id);
    }

    public boolean isUserBlockedById(Integer id) {

        logger.info("is user with id = %d blocked.");
        return userRepository.isBlockedById(id);
    }
}
