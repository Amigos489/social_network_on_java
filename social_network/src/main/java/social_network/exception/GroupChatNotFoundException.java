package social_network.exception;

public class GroupChatNotFoundException extends RuntimeException {
    public GroupChatNotFoundException(Integer id) {
        super("The group chat with the specified id = " + id + " was not found.");
    }
}
