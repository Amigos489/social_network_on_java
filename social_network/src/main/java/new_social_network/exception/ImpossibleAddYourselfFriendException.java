package new_social_network.exception;

public class ImpossibleAddYourselfFriendException extends RuntimeException {
    public ImpossibleAddYourselfFriendException() {
        super("It is impossible to add yourself as a friend.");
    }
}
