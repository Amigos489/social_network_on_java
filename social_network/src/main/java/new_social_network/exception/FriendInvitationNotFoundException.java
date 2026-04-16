package new_social_network.exception;

public class FriendInvitationNotFoundException extends RuntimeException {
    public FriendInvitationNotFoundException(Integer id) {
        super("The friend invitation with the specified id = " + id + " was not found.");
    }
}
