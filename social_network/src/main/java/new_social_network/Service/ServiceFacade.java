package new_social_network.Service;

import new_social_network.entity.FriendInvitation;
import new_social_network.entity.Post;
import new_social_network.entity.Profile;
import new_social_network.entity.User;
import new_social_network.exception.UserAlreadyFriendException;
import new_social_network.repository.PostRepository;

import java.time.LocalDate;
import java.util.List;

public class ServiceFacade {

    private UserService userService;
    private ProfileService profileService;
    private PostService postService;
    private FriendInvitationService friendInvitationService;
    private UsersFriendsService usersFriendsService;

    public ServiceFacade(UserService userService, ProfileService profileService,
                         PostService postService, FriendInvitationService friendInvitationService,
                         UsersFriendsService usersFriendsService) {
        this.userService = userService;
        this.profileService = profileService;
        this.postService = postService;
        this.friendInvitationService = friendInvitationService;
        this.usersFriendsService = usersFriendsService;
    }

    public void registerUser(String name, String surname,
                             String login, String password) {

        User user = userService.save(name, surname, login, password);

        profileService.save(user);
    }

    public void placePostInProfile(Integer id, String content) {

        profileService.findProfileById(id);

        Post post = postService.createNewPost(content);

        profileService.addPostInProfileById(id, post);
    }

    public void setStatusInProfileById(Integer id, String status) {

        profileService.findProfileById(id);

        profileService.setStatusInProfileById(id, status);
    }

    public void setBirthdayInProfileById(Integer id, LocalDate birthday) {

        profileService.findProfileById(id);

        profileService.setBirthdayInProfileById(id, birthday);
    }

    public List<Post> getAllPostInProfileById(Integer id) {

        profileService.findProfileById(id);

        return profileService.getPostOnWallProfileById(id);
    }

    public void acceptFriendInvitationById(Integer id) {

        FriendInvitation friendInvitation = friendInvitationService.acceptFriendInvitationById(id);

        usersFriendsService.addInFriend(friendInvitation.getSender().getId(), friendInvitation.getRecipient().getId());
    }

    public void deleteUserFromFriends(Integer userId, Integer friendId) {

        try {

            usersFriendsService.isUserAlreadyFriendById(userId, friendId);

            System.out.println("Пользователи даже не в друзьях!");

            return;
        } catch (UserAlreadyFriendException e) {

            usersFriendsService.deleteFromFriend(userId, friendId);
        }
    }

    public void declineFriendInvitationById(Integer id) {

        FriendInvitation friendInvitation = friendInvitationService.declineFriendInvitationById(id);
    }

    public void sendFriendInvitation(Integer userId, Integer friendId) {

        User user = userService.findUserById(userId);

        User friend = userService.findUserById(friendId);

        usersFriendsService.isUserAlreadyFriendById(userId, friendId);

        friendInvitationService.sendFriendInvitation(user, friend);
    }

    public List<FriendInvitation> findFriendInvitationUserById(Integer id) {

        return friendInvitationService.findFriendInvitationUserById(id);
    }

    public List<User> findFriendsUserById(Integer id) {
        return usersFriendsService.findFriendsUserById(id);
    }
}
