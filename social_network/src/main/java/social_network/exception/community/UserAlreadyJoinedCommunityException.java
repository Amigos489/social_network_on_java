package social_network.exception.community;

public class UserAlreadyJoinedCommunityException extends RuntimeException {
    public UserAlreadyJoinedCommunityException(Integer userid, Integer communityId) {
        super(String.format("User with id = %d has already joined the community with id = %d", userid, communityId));
    }
}
