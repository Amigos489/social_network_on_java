package social_network.exception;

public class EmailAlreadyBusyException extends RuntimeException {
    public EmailAlreadyBusyException(String message) {
        super(message);
    }
}
