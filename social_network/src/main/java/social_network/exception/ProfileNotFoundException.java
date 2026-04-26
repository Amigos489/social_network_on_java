package social_network.exception;

public class ProfileNotFoundException extends RuntimeException {
    public ProfileNotFoundException(Integer id) {
        super("The profile with the specified id = " + id + " was not found.");
    }
}
