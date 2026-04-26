package social_network.exception;

public class UserIsNotInChatException extends RuntimeException {
    public UserIsNotInChatException(Integer id) {
        super("The user with the id = " + id + " is not in the chat");
    }
}
