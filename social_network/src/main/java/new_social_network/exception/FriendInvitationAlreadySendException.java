package new_social_network.exception;

public class FriendInvitationAlreadySendException extends RuntimeException {
    public FriendInvitationAlreadySendException() {
        super("The friend invitation has already been sent");
    }
}
