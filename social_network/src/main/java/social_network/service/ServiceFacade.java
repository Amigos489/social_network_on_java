package social_network.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import social_network.dto.*;
import social_network.dto.CommunityInfoDto;
import social_network.entity.*;
import social_network.enums.Gender;
import social_network.exception.*;

import java.util.List;
import java.util.Set;

@Service
public class ServiceFacade {

    private static Logger logger = LogManager.getLogger(ServiceFacade.class);

    private final UserService userService;

    private final ProfileService profileService;

    private final MessageService messageService;

    private final ChatService chatService;

    private final PostService postService;

    private final FriendInvitationService friendInvitationService;

    private final CommunityService communityService;

    public ServiceFacade(UserService userService, ProfileService profileService,
                         MessageService messageService, ChatService chatService,
                         FriendInvitationService friendInvitationService, PostService postService,
                         CommunityService communityService) {
        this.userService = userService;
        this.profileService = profileService;
        this.messageService = messageService;
        this.chatService = chatService;
        this.friendInvitationService = friendInvitationService;
        this.postService = postService;
        this.communityService = communityService;
    }

    @Transactional
    public void registerUser(UserRegisterDto userRegisterDto) {

        logger.info("start user registration.");

        userService.checkingIfEmailOccupied(userRegisterDto.login());

        User user = userService.createUser(userRegisterDto.name(), userRegisterDto.surname(), userRegisterDto.login(), userRegisterDto.password());

        Profile profile = profileService.createProfile(user);

        logger.info("the user is registered");
    }

    @Transactional
    public void sendMessageToUser(MessageToUserDto messageToUserDto, Integer userId) {

        logger.info(String.format("send message to user with id = %d from user with id = %d.", messageToUserDto.recipientId(), userId));

        User sender = userService.findUserById(userId);

        User recipient = userService.findUserById(messageToUserDto.recipientId());

        PersonalChat personalChat = chatService.findPersonalChatByUserIds(sender, recipient);

        if (personalChat == null) {

            personalChat = chatService.createPersonalChat(sender, recipient);
        }

        Message message = messageService.createMessage(messageToUserDto.text(),
                sender,
                personalChat);

        personalChat.getMessages().add(message);

        logger.info(String.format("a message has been sent to the user with id = %d from the user with id = %d.",
                messageToUserDto.recipientId(),
                userId));
    }

    @Transactional
    public void sendFriendInvitation(Integer senderId, Integer recipientId) {

        logger.info(String.format("send friend invitation to user with id = %d from user with id = %d.", senderId, recipientId));

        User sender = userService.findUserById(senderId);

        User recipient = userService.findUserById(recipientId);

        if (senderId.equals(recipientId)) {
            logger.error("You can't send an friend invitation to yourself.");
            throw new FriendInvitationException(String.format("You can't send an friend invitation to yourself."));
        }

        if (friendInvitationService.checkIfUsersFriendsById(senderId, recipientId)) {

            logger.error(String.format("Users with id = %d and id = %d are already friends.", senderId, recipientId));
            throw new FriendInvitationException(String.format("Users with id = %d and id = %d are already friends.", senderId, recipientId));
        }

        if (friendInvitationService.checkActiveFriendInvitationByUserIds(senderId, recipientId)) {

            throw new FriendInvitationException("The friend invitation has already been sent.");
        }

        FriendInvitation friendInvitation = friendInvitationService.createFriendInvitation(sender, recipient);
    }

    @Transactional
    public void acceptFriendInvitation(Integer senderId, Integer recipientId, Integer userId) {

        logger.info("accept a friend invitation user c id = {} from user with id = {}", recipientId, senderId);

        if (!userId.equals(recipientId)) {
            logger.error("the user with id = {} is not the recipient of the friend invitation.", userId);
            throw new FriendInvitationException(String.format("the user with id = %d is not the recipient of the friend invitation.", userId));
        }

        User sender = userService.findUserById(senderId);

        User recipient = userService.findUserById(recipientId);

        friendInvitationService.acceptFriendInvitationById(sender, recipient);
        logger.info("user with id = {} has accepted a friend request from User with id = {}.", recipientId, senderId);
    }

    @Transactional
    public void declineFriendInvitation(Integer senderId, Integer recipientId, Integer userId) {

        logger.info("decline a friend invitation user c id = {} from user with id = {}", recipientId, senderId);

        if (!userId.equals(senderId) && !userId.equals(recipientId)) {
            logger.error("the user with id = {} is not the recipient or sender of the friend invitation.", userId);
            throw new FriendInvitationException(String.format("the user with id = %d is not the recipient or sender of the friend invitation.", userId));
        }

        if (friendInvitationService.checkIfUsersFriendsById(senderId, recipientId)) {
            logger.error("the user with id = {} is not the recipient or sender of the friend invitation.", userId);
            throw new FriendInvitationException(String.format("Users with id = %d and id = %d are already friends.", senderId, recipientId));
        }

        User sender = userService.findUserById(senderId);

        User recipient = userService.findUserById(recipientId);

        friendInvitationService.declineFriendInvitationById(sender, recipient);

        logger.info(String.format("user with id = %d has declined a friend request from User with id = %d.", recipientId, senderId));
    }

    @Transactional(readOnly = true)
    public List<ReceivedFriendInvitationDto> findSentFriendInvitation(Integer senderId) {

        logger.info("find sent friend invitation");

        User sender = userService.findUserById(senderId);

        return friendInvitationService.findSentFriendInvitationByUser(sender);
    }

    @Transactional(readOnly = true)
    public List<SentFriendInvitationDto> findReceivedFriendInvitation(Integer recipientId) {

        logger.info("find received friend invitation");

        User recipient = userService.findUserById(recipientId);

        return friendInvitationService.findReceivedFriendInvitationByUser(recipient);
    }

    @Transactional
    public void deleteUserFromFriend(Integer userId, Integer friendId) {

        logger.info(String.format("delete user with id = %d from friend with id = %d", userId, friendId));

        User user = userService.findUserById(userId);

        User friend = userService.findUserById(friendId);

        if (!friendInvitationService.checkIfUsersFriendsById(userId, friendId)) {
            logger.error(String.format("Users with id = %d and id = %d are not friends.", userId, friendId));
            throw new FriendInvitationException(String.format("Users with id = %d and id = %d are not friends.", userId, friendId));
        }

        friendInvitationService.deleteUserFromFriendByUser(user, friend);

        logger.info(String.format("user with id = %d from friend with id = %d deleted.", userId, friendId));
    }

    @Transactional
    public void publishPostInProfile(String content, Integer profileId) {

        logger.info(String.format("publish post in profile with id = %d.", profileId));

        Profile profile = profileService.findProfileById(profileId);

        User user = profile.getUser();

        if (user.isBlocked()) {
            logger.error("The user is blocked and cannot publish posts in profile.");
            throw new UserBlockedException("The user is blocked and cannot publish posts in profile.");
        }

        Post post = postService.createPost(content);

        profileService.addPostInProfile(profileId, post);

        logger.info(String.format("post in profile with id = %d published.", profileId));
    }

    @Transactional
    public void deletePostFromProfile(Integer postId, Integer profileId) {

        logger.info(String.format("delete post from profile with id = %d", profileId));

        Profile profile = profileService.findProfileById(profileId);

        Post post = postService.findPostById(postId);

        profileService.deletePostFromProfileById(profileId, post);

        postService.deletePostById(postId);

        logger.info(String.format("post from profile with id = %d deleted.", profileId));
    }

    @Transactional
    public void specifyStatusInProfile(Integer profileId, String status) {

        logger.info(String.format("specify status in profile with id = %d.", profileId));

        Profile profile = profileService.findProfileById(profileId);

        profileService.setStatusProfile(profile, status);
    }

    @Transactional
    public GroupChatDto createGroupChat(CreateGroupChatDto createGroupChatDto) {

        logger.info("creating a group chat. users to add {}. creator id = {}", createGroupChatDto.userIds().toString(), createGroupChatDto.creatorId());

        User creator = userService.findUserById(createGroupChatDto.creatorId());

        Set<User> users = userService.findUsersByIds(createGroupChatDto.userIds());

        GroupChatDto groupChatDto = chatService.createGroupChat(users, creator);

        logger.info("group chat with id = {}. creator id = {} created.",
                groupChatDto.id(),
                groupChatDto.creator().id());

        return groupChatDto;
    }

    @Transactional
    public void deleteGroupChat(Integer chatId, Integer creatorId) {

        logger.info(String.format("delete group chat with id = %d.", chatId));

        User creator = userService.findUserById(creatorId);

        messageService.deleteAllMessageFromChat(chatId);

        chatService.deleteGroupChatById(chatId, creator);

        logger.info(String.format("group chat with id = %d deleted.", chatId));
    }

    @Transactional
    public void addUserInGroupChat(Integer userIdForAdd, Integer groupChatId, Integer userIdWhoAdds) {

        logger.info(String.format("add user with id = %d in group chat with id = %d", userIdForAdd, groupChatId));

        if (userIdForAdd.equals(userIdWhoAdds)) {
            logger.error("The user cannot add himself.");
            throw new ChatException("The user cannot add himself.");
        }

        User userForAdd = userService.findUserById(userIdForAdd);

        User userWhoAdds = userService.findUserById(userIdWhoAdds);

        chatService.addUserInGroupChatById(userForAdd, groupChatId, userWhoAdds);

        logger.info(String.format("user with id = %d added in group chat with id = %d", userIdForAdd, groupChatId));
    }

    @Transactional
    public void leaveFromGroupChat(Integer groupChatId, Integer userId) {

        logger.info(String.format("user with id = %d leave from group chat with id = %d.", userId, groupChatId));

        User user = userService.findUserById(userId);

        GroupChat groupChat = chatService.findGroupChatById(groupChatId);

        chatService.leaveFromGroupChat(groupChatId, user);

        logger.info(String.format("user with id = %d left the group chat with id = %d.", userId, groupChat));
    }

    @Transactional
    public void kickUserFromGroupChat(Integer userIdForDelete, Integer groupChatId, Integer userIdWhoDeletes) {

        logger.info(String.format("kick user with id = %d from group chat with id = %d", userIdForDelete, groupChatId));

        if (userIdForDelete.equals(userIdWhoDeletes)) {
            logger.error(String.format("the user with id = %d is kicked out of group chat with id = %d.", userIdForDelete, groupChatId));
            throw new ChatException(String.format("the user with id = %d is kicked out of group chat with id = %d.", userIdForDelete, groupChatId));
        }

        User userForDelete = userService.findUserById(userIdForDelete);

        User userWhoDeletes = userService.findUserById(userIdWhoDeletes);

        chatService.kickUserFromGroupChatById(userForDelete, groupChatId, userWhoDeletes);

        logger.info(String.format("the user with id = %d is kicked out of group chat with id = %d.", userIdForDelete, groupChatId));
    }

    @Transactional
    public void sendMessageInGroupChat(MessageToGroupChatDto messageToGroupChatDto, Integer userId) {

        Integer chatId = messageToGroupChatDto.chatId();

        String text = messageToGroupChatDto.text();

        logger.info(String.format("send message in group chat with id = %d from user with id = %d", chatId, userId));

        User sender = userService.findUserById(userId);

        GroupChat groupChat = chatService.findGroupChatById(chatId);

        Message message = messageService.createMessage(text, sender, groupChat);

        chatService.sendMessageInGroupChat(groupChat, message);
    }

    @Transactional
    public List<MessageDto> readAllMessagesFromGroupChat(Integer chatId, Integer userId) {

        logger.info(String.format("read all messages from group chat with id = %d", chatId));

        User user = userService.findUserById(userId);

        GroupChat groupChat = chatService.findGroupChatById(chatId);

        List<Message> messages = chatService.getAllMessageFromGroupChatById(groupChat, user);

        return messageService.markMessageReadInChatById(groupChat, user);
    }

    @Transactional
    public List<MessageDto> readAllMessagesFromPersonalChat(Integer firstUserId, Integer secondUserId) {

        logger.info(String.format("read all messages from a personal chat between users with id = %d and %d", firstUserId, secondUserId));

        User firstUser = userService.findUserById(firstUserId);

        User secondUser = userService.findUserById(secondUserId);

        PersonalChat chat = chatService.findPersonalChatByUserIds(firstUser, secondUser);

        List<Message> messages = chatService.getAllMessageFromPersonalChat(firstUser, secondUser);

        return messageService.markMessageReadInChatById(chat, firstUser);
    }

    @Transactional
    public CommunityInfoDto createCommunity(CommunityInfoDto communityInfoDto, Integer userId) {

        logger.info("create community");

        User creator = userService.findUserById(userId);

        if (creator.isBlocked()) {
            logger.error("The user is blocked and cannot create communities.");
            throw new UserBlockedException("The user is blocked and cannot create communities.");
        }

       return communityService.createCommunity(communityInfoDto.name(), communityInfoDto.description(), creator);
    }

    @Transactional
    public void deleteCommunity(Integer communityId, Integer userId) {

        logger.info(String.format("delete community with id = %d.", userId, communityId));

        User user = userService.findUserById(userId);

        Community community = communityService.findCommunityById(communityId);

        communityService.deleteCommunity(community, user);

        logger.info(String.format("community with id = %d deleted.", userId, communityId));
    }

    @Transactional
    public void joinCommunity(Integer communityId, Integer userId) {

        logger.info(String.format("user with id = %d join from community with id = %d.", userId, communityId));

        User user = userService.findUserById(userId);

        Community community = communityService.findCommunityById(communityId);

        communityService.userJoiningCommunity(user, community);

        logger.info(String.format("user with id = %d joined from community with id = %d.", userId, communityId));
    }

    @Transactional
    public void leaveCommunity(Integer communityId, Integer userId) {

        logger.info(String.format("user with id = %d leave from community with id = %d.", userId, communityId));

        User user = userService.findUserById(userId);

        Community community = communityService.findCommunityById(communityId);

        communityService.userLeavingCommunity(user, community);

        logger.info(String.format("user with id = %d left the community with id = %d.", userId, communityId));
    }

    @Transactional
    public PostInCommunityDto publishPostInCommunity(CreatePostInCommunityDto createPostInCommunityDto, Integer userId) {

        logger.info(String.format("publish post in community with id = %d.", createPostInCommunityDto.communityId()));

        Community community = communityService.findCommunityById(createPostInCommunityDto.communityId());

        User authorPost = userService.findUserById(userId);

        logger.info(String.format("post in community with id = %d published.", createPostInCommunityDto.communityId()));

        return communityService.publishPostInCommunityById(createPostInCommunityDto.content(), community, authorPost);
    }

    @Transactional
    public void deletePostinCommunity(Integer postId, Integer communityId, Integer userId) {

        logger.info(String.format("delete post in community with id = %d.", communityId));

        Community community = communityService.findCommunityById(communityId);

        User user = userService.findUserById(userId);

        communityService.deletePostFromCommunity(postId, community, user);

        logger.info(String.format("post in community with id = %d deleted.", communityId));
    }

    @Transactional
    public void kickUserFromCommunity(Integer userIdForDelete, Integer communityId, Integer userIdWhoDeletes) {

        logger.info(String.format("kick user with id = %d from community with id = %d. id user who deletes = %d", userIdForDelete, communityId ,userIdWhoDeletes));

        User userForDelete = userService.findUserById(userIdForDelete);

        User userWhoDeletes = userService.findUserById(userIdWhoDeletes);

        Community community = communityService.findCommunityById(communityId);

        communityService.kickUserFromCommunityById(userForDelete, community, userWhoDeletes);

        logger.info(String.format("the user with id = %d is kicked out of community with id = %d.", userIdForDelete, communityId));
    }

    @Transactional(readOnly = true)
    public ProfileInfoDto findProfile(Integer id) {
        return profileService.findProfile(id);
    }

    public CommunityDto findCommunity(Integer id) {
        return communityService.getInfoCommunityById(id);
    }

    public List<CommunityInfoDto> getCommunitiesUserJoined(int id) {

        User user = userService.findUserById(id);

        return communityService.findCommunitiesUserJoined(user);
    }

    public Set<UserDto> getUsersWhoJoinedCommunity(int id) {

        Community community = communityService.findCommunityById(id);

        return communityService.findUsersJoinedCommunity(id);
    }

    public List<ProfileInfoDto> findProfilesByCriteria(FindProfileCriteriaDto findProfileCriteriaDto) {
        return profileService.findProfilesByCriteria(findProfileCriteriaDto);
    }

    @Transactional
    public void setStatusInProfile(Integer id, String status) {

        Profile profile = profileService.findProfileById(id);

        profileService.setStatusProfile(profile, status);
    }

    @Transactional
    public void setAgeInProfile(Integer id, Integer age) {

        Profile profile = profileService.findProfileById(id);

        profileService.setAgeProfile(profile, age);
    }

    @Transactional
    public void setGenderInProfile(Integer id, Gender gender) {

        Profile profile = profileService.findProfileById(id);

        profileService.setGenderProfile(profile, gender);
    }

    @Transactional(readOnly = true)
    public List<UserDto> getFriends(Integer currentUserId) {
        return friendInvitationService.findFriendsByUserId(currentUserId);
    }
}
