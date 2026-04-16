package new_social_network.Service;

import new_social_network.entity.FriendInvitation;
import new_social_network.entity.User;
import new_social_network.enums.FriendInvitationStatus;
import new_social_network.exception.FriendInvitationAlreadySendException;
import new_social_network.exception.FriendInvitationNotFoundException;
import new_social_network.exception.ImpossibleAddYourselfFriendException;
import new_social_network.repository.FriendInvitationRepository;

import java.util.List;

public class FriendInvitationService {

    private FriendInvitationRepository friendInvitationRepository;

    public FriendInvitationService(FriendInvitationRepository friendInvitationRepository) {
        this.friendInvitationRepository = friendInvitationRepository;
    }

    public FriendInvitation sendFriendInvitation(User sender, User recipient) {

        if (sender.getId().equals(recipient.getId())) {
            throw new ImpossibleAddYourselfFriendException();
        }

        FriendInvitation friendInvitation = friendInvitationRepository.findWaitingFriendInvitationBySenderIdAndRecipientId(sender.getId(), recipient.getId());

        if (friendInvitation != null) {
            throw new FriendInvitationAlreadySendException();
        }

        return friendInvitationRepository.save(sender, recipient, FriendInvitationStatus.WAITING);
    }

    public FriendInvitation acceptFriendInvitationById(Integer id) {

        FriendInvitation friendInvitation = findFriendInvitationById(id);

        friendInvitationRepository.updateStatusById(id, FriendInvitationStatus.ACCEPTED);

        return friendInvitation;
    }

    public FriendInvitation declineFriendInvitationById(Integer id) {

        FriendInvitation friendInvitation = findFriendInvitationById(id);

        friendInvitationRepository.updateStatusById(id, FriendInvitationStatus.REJECTED);

        return friendInvitation;
    }

    public FriendInvitation findFriendInvitationById(Integer id) {

        FriendInvitation friendInvitation = friendInvitationRepository.findById(id);

        if (friendInvitation == null) {
            throw new FriendInvitationNotFoundException(id);
        }

        return friendInvitation;
    }

    public List<FriendInvitation> findFriendInvitationUserById(Integer id) {

        return friendInvitationRepository.findFriendInvitationUserById(id);
    }
}
