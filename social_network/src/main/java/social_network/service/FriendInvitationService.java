package social_network.Service;

import social_network.entity.FriendInvitation;
import social_network.entity.User;
import social_network.enums.FriendInvitationStatus;
import social_network.exception.FriendInvitationException;
import social_network.primarykey.FriendInvitationPrimaryKey;
import social_network.repository.FriendInvitationRepository;

import java.util.List;

public class FriendInvitationService {

    private FriendInvitationRepository friendInvitationRepository;

    public FriendInvitationService(FriendInvitationRepository friendInvitationRepository) {
        this.friendInvitationRepository = friendInvitationRepository;
    }

    public FriendInvitation createFriendInvitation(User sender, User recipient) {

        FriendInvitation friendInvitation = new FriendInvitation(sender, recipient, FriendInvitationStatus.WAITING);

        return friendInvitationRepository.create(friendInvitation);
    }

    public FriendInvitation acceptFriendInvitationById(User sender, User recipient) {

        FriendInvitationPrimaryKey primaryKey = new FriendInvitationPrimaryKey(sender, recipient);

        FriendInvitation friendInvitation = friendInvitationRepository.findById(primaryKey);

        if (friendInvitation == null) {
            throw new FriendInvitationException(String.format("Friend invitation has been found between users with id = %d and id = %d", sender.getId(), recipient.getId()));
        }

        return friendInvitationRepository.updateStatus(friendInvitation, FriendInvitationStatus.ACCEPTED);
    }

    public FriendInvitation declineFriendInvitationById(User sender, User recipient) {

        FriendInvitationPrimaryKey primaryKey = new FriendInvitationPrimaryKey(sender, recipient);

        FriendInvitation friendInvitation = friendInvitationRepository.findById(primaryKey);

        if (friendInvitation == null) {
            throw new FriendInvitationException(String.format("Friend invitation where the sender is a user with id = %d and the recipient has id = %d is not found", sender.getId(), recipient.getId()));
        }

        return friendInvitationRepository.updateStatus(friendInvitation, FriendInvitationStatus.DECLINE);
    }

    public List<FriendInvitation> findSentFriendInvitationByUser(User sender) {

        return friendInvitationRepository.findSentByUser(sender);
    }

    public List<FriendInvitation> findReceivedFriendInvitationByUser(User recipient) {

        return friendInvitationRepository.findReceivedByUserId(recipient);
    }

    public boolean checkIfUsersFriendsById(Integer firstUserId, Integer secondUserId) {

        return friendInvitationRepository.isUsersFriendsById(firstUserId, secondUserId);
    }

    public FriendInvitation findActiveFriendInvitationByUserIds(Integer firstUserId, Integer secondUserId) {

        return friendInvitationRepository.findActiveByUserIds(firstUserId, secondUserId);
    }

    public void deleteUserFromFriendByUser(User user, User friend) {

        friendInvitationRepository.deleteUserFromFriendByUserIds(user.getId(), friend.getId());
    }
}
