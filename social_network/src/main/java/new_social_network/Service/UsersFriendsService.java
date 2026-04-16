package new_social_network.Service;

import new_social_network.entity.User;
import new_social_network.entity.UsersFriends;
import new_social_network.exception.FriendInvitationNotFoundException;
import new_social_network.exception.UserAlreadyFriendException;
import new_social_network.repository.UsersFriendsRepository;

import java.util.List;

public class UsersFriendsService {

    private UsersFriendsRepository usersFriendsRepository;

    public UsersFriendsService(UsersFriendsRepository usersFriendsRepository) {
        this.usersFriendsRepository = usersFriendsRepository;
    }

    public UsersFriends addInFriend(Integer userId, Integer friendId) {

        return usersFriendsRepository.create(userId, friendId);
    }

    public void deleteFromFriend(Integer userId, Integer friendId) {

        usersFriendsRepository.delete(userId, friendId);
    }

    public List<User> findFriendsUserById(Integer id) {

        return usersFriendsRepository.findFriendsUserById(id);
    }

    public void isUserAlreadyFriendById(Integer userId, Integer friendId) {

        boolean isUserAlreadyInFriend = usersFriendsRepository.isUserAlreadyFriendById(userId, friendId);

        if (isUserAlreadyInFriend) {
            throw new UserAlreadyFriendException(userId, friendId);
        }
    }
}
