package social_network.Service;

import social_network.entity.*;
import social_network.exception.*;

import java.time.LocalDate;
import java.util.List;

public class ServiceFacade {

    private UserService userService;

    private ProfileService profileService;

    private MessageService messageService;

    private ChatService chatService;

    private PostService postService;

    private FriendInvitationService friendInvitationService;

    private CommunityService communityService;

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

    public void registerUser(String name, String surname,
                             String login, String password) {

        try {

            // Проверка, что логин не занят другим пользователем.

            User user = userService.findUserByLogin(login);

            throw new ErrorRegistrationException("This login is already being used by another user.");

        } catch (UserNotFoundException e) {

            User user = userService.createUser(name, surname, login, password);

            Profile profile = profileService.createProfile(user);
        }
    }

    public void sendMessageToUser(Integer senderId, Integer recipientId, String text) {

        User sender = userService.findUserById(senderId);

        User recipient = userService.findUserById(recipientId);

        PersonalChat personalChat = chatService.findPersonalChatByUserIds(sender, recipient);

        if (personalChat == null) {

            personalChat = chatService.createPersonalChat(sender, recipient);
        }

        Message message = messageService.createMessage(text, sender, personalChat);

        personalChat.getMessages().add(message);
    }

    public void sendFriendInvitation(Integer senderId, Integer recipientId) {

        User sender = userService.findUserById(senderId);

        User recipient = userService.findUserById(recipientId);

        //Нельзя отправить приглашение в друзья самому себе

        if (senderId.equals(recipientId)) {
            return;
        }

        //Проверка, что пользователи уже друзья

        if (friendInvitationService.checkIfUsersFriendsById(senderId, recipientId)) {

            throw new FriendInvitationException(String.format("Users with id = %d and id = %d are already friends.", senderId, recipientId));
        }

        //Проверка, что приглашение в друзья уже отправлено

        if (friendInvitationService.findActiveFriendInvitationByUserIds(senderId, recipientId) != null) {

            throw new FriendInvitationException("The friend invitation has already been sent.");
        }

        FriendInvitation friendInvitation = friendInvitationService.createFriendInvitation(sender, recipient);
    }

    public void acceptFriendInvitation(Integer senderId, Integer recipientId, Integer userId) {

        if (!userId.equals(senderId) && !userId.equals(recipientId)) {
            return;
        }

        if (senderId.equals(userId)) {
            throw new FriendInvitationException("The sender cannot accept the friend invitation.");
        }

        User sender = userService.findUserById(senderId);

        User recipient = userService.findUserById(recipientId);

        friendInvitationService.acceptFriendInvitationById(sender, recipient);
    }

    public void declineFriendInvitation(Integer senderId, Integer recipientId, Integer userId) {

        if (!userId.equals(senderId) && !userId.equals(recipientId)) {
            return;
        }

        if (friendInvitationService.checkIfUsersFriendsById(senderId, recipientId)) {

            throw new FriendInvitationException(String.format("Users with id = %d and id = %d are already friends.", senderId, recipientId));
        }

        User sender = userService.findUserById(senderId);

        User recipient = userService.findUserById(recipientId);

        friendInvitationService.declineFriendInvitationById(sender, recipient);
    }

    public List<FriendInvitation> findSentFriendInvitation(Integer senderId) {

        User sender = userService.findUserById(senderId);

        return friendInvitationService.findSentFriendInvitationByUser(sender);
    }

    public List<FriendInvitation> findReceivedFriendInvitation(Integer recipientId) {

        User recipient = userService.findUserById(recipientId);

        return friendInvitationService.findReceivedFriendInvitationByUser(recipient);
    }

    public void deleteUserFromFriend(Integer userId, Integer friendId, Integer userIdWhoDeletes) {

        if (!userIdWhoDeletes.equals(userId) && !userIdWhoDeletes.equals(friendId)) {
            return;
        }

        User user = userService.findUserById(userId);

        User friend = userService.findUserById(friendId);

        if (!friendInvitationService.checkIfUsersFriendsById(userId, friendId)) {

            throw new FriendInvitationException(String.format("Users with id = %d and id = %d are not friends.", userId, friendId));
        }

        friendInvitationService.deleteUserFromFriendByUser(user, friend);
    }

    public void publishPostInProfile(String content, Integer profileId) {

        Profile profile = profileService.findProfileById(profileId);

        User user = profile.getUser();

        if (user.isBlocked()) {

            throw new UserBlockedException("The user is blocked and cannot publish posts in profile.");
        }

        Post post = postService.createPost(content);

        profileService.addPostInProfile(profileId, post);
    }

    public void deletePostFromProfile(Integer postId, Integer profileId) {

        Profile profile = profileService.findProfileById(profileId);

        Post post = postService.findPostById(postId);

        profileService.deletePostFromProfileById(profileId, post);

        postService.deletePostById(postId);
    }

    public void specifyBirthdayInProfile(Integer profileId, LocalDate birthday) {

        Profile profile = profileService.findProfileById(profileId);

        profileService.setBirthdayProfile(profile, birthday);
    }

    public void specifyStatusInProfile(Integer profileId, String status) {

        Profile profile = profileService.findProfileById(profileId);

        profileService.setStatusProfile(profile, status);
    }

    public void createGroupChat(List<Integer> userIdsForAdd, Integer creatorId) {

        User creator = userService.findUserById(creatorId);

        List<User> users = userService.findUsersByIds(userIdsForAdd);

        chatService.createGroupChat(users, creator);
    }

    public void deleteGroupChat(Integer chatId, Integer creatorId) {

        User creator = userService.findUserById(creatorId);

        messageService.deleteAllMessageFromGroupChat(chatId);

        chatService.deleteGroupChatById(chatId, creator);
    }

    public void addUserInGroupChat(Integer userIdForAdd, Integer groupChatId, Integer userIdWhoAdds) {

        if (userIdForAdd.equals(userIdWhoAdds)) {
            return;
        }

        User userForAdd = userService.findUserById(userIdForAdd);

        User userWhoAdds = userService.findUserById(userIdWhoAdds);

        chatService.addUserInGroupChatById(userForAdd, groupChatId, userWhoAdds);
    }

    public void leaveFromGroupChat(Integer groupChatId, Integer userId) {

        User user = userService.findUserById(userId);

        GroupChat groupChat = chatService.findGroupChatById(groupChatId);

        chatService.leaveFromGroupChat(groupChatId, user);
    }

    public void kickUserFromGroupChat(Integer userIdForDelete, Integer groupChatId, Integer userIdWhoDeletes) {

        if (userIdForDelete.equals(userIdWhoDeletes)) {
            return;
        }

        User userForDelete = userService.findUserById(userIdForDelete);

        User userWhoDeletes = userService.findUserById(userIdWhoDeletes);

        chatService.kickUserFromGroupChatById(userForDelete, groupChatId, userWhoDeletes);
    }

    public void sendMessageInGroupChat(String text, Integer senderId, Integer chatId) {

        User sender = userService.findUserById(senderId);

        if (!chatService.checkUserInChatById(chatId, senderId)) {

            throw new UserIsNotInChatException(senderId);
        }

        GroupChat groupChat = chatService.findGroupChatById(chatId);

        Message message = messageService.createMessage(text, sender, groupChat);

        groupChat.getMessages().add(message);
    }

    public List<Message> readAllMessagesFromGroupChat(Integer chatId, Integer userId) {

        User user = userService.findUserById(userId);

        GroupChat groupChat = chatService.findGroupChatById(chatId);

        List<Message> messages = chatService.getAllMessageFromGroupChatById(groupChat, user);

        messageService.markMessageReadInChatById(groupChat, user);

        return messages;
    }

    public List<Message> readAllMessagesFromPersonalChat(Integer firstUserId, Integer secondUserId) {

        User firstUser = userService.findUserById(firstUserId);

        User secondUser = userService.findUserById(secondUserId);

        PersonalChat chat = chatService.findPersonalChatByUserIds(firstUser, secondUser);

        List<Message> messages = chatService.getAllMessageFromPersonalChat(firstUser, secondUser);

        messageService.markMessageReadInChatById(chat, firstUser);

        return messages;
    }

    public void createCommunity(String name, String description, Integer creatorId) {

        User creator = userService.findUserById(creatorId);

        if (creator.isBlocked()) {
            throw new UserBlockedException("The user is blocked and cannot create communities.");
        }

        Community community = communityService.createCommunity(name, description, creator);
    }

    public void deleteCommunity(Integer communityId, Integer userId) {

        User user = userService.findUserById(userId);

        Community community = communityService.findCommunityById(communityId);

        communityService.deleteCommunityById(community, user);
    }

    public void joinCommunity(Integer communityId, Integer userId) {

        User user = userService.findUserById(userId);

        Community community = communityService.findCommunityById(communityId);

        communityService.joinCommunityById(user, community);
    }

    public void leaveCommunity(Integer communityId, Integer userId) {

        User user = userService.findUserById(userId);

        Community community = communityService.findCommunityById(communityId);

        communityService.leaveCommunityById(user, community);
    }

    public void publishPostCommunity(String content, Integer communityId, Integer authorPostId) {

        Community community = communityService.findCommunityById(communityId);

        User authorPost = userService.findUserById(authorPostId);

        Post post = postService.createPost(content);

        communityService.publishPostInCommunityById(post, community, authorPost);
    }

    public void deletePostinCommunity(Integer postId, Integer communityId, Integer userId) {

        Post post = postService.findPostById(postId);

        Community community = communityService.findCommunityById(communityId);

        User user = userService.findUserById(userId);

        communityService.deletePostFromCommunity(post, community, user);

        postService.deletePostById(postId);
    }

    public void kickUserFromCommunity(Integer userIdForDelete, Integer communityId, Integer userIdWhoDeletes) {

        User userForDelete = userService.findUserById(userIdForDelete);

        User userWhoDeletes = userService.findUserById(userIdWhoDeletes);

        Community community = communityService.findCommunityById(communityId);

        communityService.kickUserFromCommunityById(userForDelete, community, userWhoDeletes);
    }
}
