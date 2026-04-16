package new_social_network.repository;

import new_social_network.entity.User;
import new_social_network.entity.UsersFriends;
import new_social_network.primarykey.UsersFriendsPrimaryKey;
import org.hibernate.Session;

import java.util.List;

public class UsersFriendsRepository extends AbstractRepository<UsersFriends, UsersFriendsPrimaryKey> {

    public UsersFriendsRepository(Session session) {
       super(UsersFriends.class, session);
    }

    public UsersFriends create(Integer userId, Integer friendId) {

        UsersFriendsPrimaryKey primaryKeyForUser = new UsersFriendsPrimaryKey(userId, friendId);

        UsersFriends usersFriendsForUser = new UsersFriends(primaryKeyForUser);

        UsersFriendsPrimaryKey primaryKeyForFriend = new UsersFriendsPrimaryKey(friendId, userId);

        UsersFriends usersFriendsForFriend = new UsersFriends(primaryKeyForFriend);

        super.create(usersFriendsForUser);

        super.create(usersFriendsForFriend);

        return usersFriendsForUser;
    }

    public void delete(Integer userId, Integer friendId) {

        UsersFriendsPrimaryKey primaryKeyForUser = new UsersFriendsPrimaryKey(userId, friendId);

        UsersFriendsPrimaryKey primaryKeyForFriend = new UsersFriendsPrimaryKey(friendId, userId);

        UsersFriends usersFriendsForUser = super.findById(primaryKeyForUser);

        UsersFriends usersFriendsForFriend = super.findById(primaryKeyForFriend);

        super.delete(usersFriendsForUser);

        super.delete(usersFriendsForFriend);

        session.flush();
    }

    public List<User> findFriendsUserById(Integer id) {

        String sql = "SELECT * FROM users u JOIN users_friends uf ON u.id = uf.friend_id WHERE uf.user_id = " + id;

        return session.createNativeQuery(sql, User.class).list();
    }

    public boolean isUserAlreadyFriendById(Integer userId,  Integer friendId) {

        String sql = "SELECT * FROM users_friends uf WHERE (uf.user_id = :userId AND uf.friend_id = :friendId) " +
                "OR (uf.user_id = :friendId AND uf.friend_id = :userId)";

        List<UsersFriends> usersFriends = session.createNativeQuery(sql, UsersFriends.class)
                .setParameter("userId", userId)
                .setParameter("friendId", friendId).list();

        if (!usersFriends.isEmpty()) {
            return true;
        }

        return false;
    }
}
