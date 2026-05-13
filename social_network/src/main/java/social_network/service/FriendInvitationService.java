package social_network.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import social_network.dto.ReceivedFriendInvitationDto;
import social_network.dto.SentFriendInvitationDto;
import social_network.dto.UserDto;
import social_network.entity.FriendInvitation;
import social_network.entity.User;
import social_network.enums.FriendInvitationStatus;
import social_network.exception.NotFoundException;
import social_network.mapper.FriendInvitationMapper;
import social_network.mapper.UserMapper;
import social_network.primarykey.FriendInvitationPrimaryKey;
import social_network.repository.FriendInvitationRepository;

import java.util.List;

@Service
public class FriendInvitationService {

    private Logger logger = LogManager.getLogger(FriendInvitationService.class);

    private final String messageFriendInvitationNotFound = "Friend invitation where the sender is a user with id = %d and the recipient has id = %d is not found";

    private final FriendInvitationMapper friendInvitationMapper;

    private final UserMapper userMapper;

    private final FriendInvitationRepository friendInvitationRepository;

    public FriendInvitationService(FriendInvitationMapper friendInvitationMapper, UserMapper userMapper, FriendInvitationRepository friendInvitationRepository) {
        this.friendInvitationMapper = friendInvitationMapper;
        this.userMapper = userMapper;
        this.friendInvitationRepository = friendInvitationRepository;
    }

    public FriendInvitation createFriendInvitation(User sender, User recipient) {

        logger.info("create friend invitation.");

        FriendInvitation friendInvitation = new FriendInvitation(sender, recipient, FriendInvitationStatus.WAITING);

        return friendInvitationRepository.create(friendInvitation);
    }

    public FriendInvitation acceptFriendInvitationById(User sender, User recipient) {


        FriendInvitationPrimaryKey primaryKey = new FriendInvitationPrimaryKey(sender, recipient);

        FriendInvitation friendInvitation = friendInvitationRepository.findById(primaryKey);

        if (friendInvitation == null) {
            throw new NotFoundException(String.format(messageFriendInvitationNotFound, sender.getId(), recipient.getId()));
        }

        return friendInvitationRepository.updateStatus(friendInvitation, FriendInvitationStatus.ACCEPTED);
    }

    public FriendInvitation declineFriendInvitationById(User sender, User recipient) {

        FriendInvitationPrimaryKey primaryKey = new FriendInvitationPrimaryKey(sender, recipient);

        FriendInvitation friendInvitation = friendInvitationRepository.findById(primaryKey);

        if (friendInvitation == null) {
            throw new NotFoundException(String.format(messageFriendInvitationNotFound, sender.getId(), recipient.getId()));
        }

        return friendInvitationRepository.updateStatus(friendInvitation, FriendInvitationStatus.DECLINE);
    }

    public List<ReceivedFriendInvitationDto> findSentFriendInvitationByUser(User sender) {

        logger.info("find Sent Friend Invitation By User");

        List<FriendInvitation> sentFriendInvitations = friendInvitationRepository.findSentByUser(sender);
        return friendInvitationMapper.toReceivedFriendInvitation(sentFriendInvitations);
    }

    public List<SentFriendInvitationDto> findReceivedFriendInvitationByUser(User recipient) {

        logger.info("find Received Friend Invitation By User");

        List<FriendInvitation> sentFriendInvitations = friendInvitationRepository.findReceivedByUserId(recipient);
        return friendInvitationMapper.toSentFriendInvitation(sentFriendInvitations);
    }

    public boolean checkIfUsersFriendsById(Integer firstUserId, Integer secondUserId) {

        logger.info("checkIfUsersFriendsById");

        return friendInvitationRepository.isUsersFriendsById(firstUserId, secondUserId);
    }

    public boolean checkActiveFriendInvitationByUserIds(Integer firstUserId, Integer secondUserId) {

        logger.info("checkActiveFriendInvitationByUserIds");

        FriendInvitation friendInvitation = friendInvitationRepository.findActiveByUserIds(firstUserId, secondUserId);

        if (friendInvitation != null) {
            return true;
        }

        return false;
    }

    public void deleteUserFromFriendByUser(User user, User friend) {

        logger.info("deleteUserFromFriendByUser");

        friendInvitationRepository.deleteUserFromFriendByUserIds(user.getId(), friend.getId());
    }

    public List<UserDto> findFriendsByUserId(Integer userId) {

        logger.info("findFriendsByUserId");

        List<User> friends = friendInvitationRepository.findFriendsByUserId(userId);

        return userMapper.doUserDtoList(friends);
    }
}
