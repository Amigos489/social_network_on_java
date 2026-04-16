package new_social_network.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Integer id) {
        super("The user with the specified id = " + id + " was not found.");
    }
}
