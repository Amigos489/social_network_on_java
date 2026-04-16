package new_social_network.exception;

public class UserAlreadyFriendException extends RuntimeException {
    public UserAlreadyFriendException(Integer userId, Integer friendId) {
        super("The user with the specified id = "
                + friendId +
                " is already a friend of the user with id = "
                + userId);
    }
}
