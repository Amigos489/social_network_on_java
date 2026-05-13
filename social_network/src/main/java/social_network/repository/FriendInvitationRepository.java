package social_network.repository;

import org.springframework.stereotype.Repository;
import social_network.entity.FriendInvitation;
import social_network.entity.User;
import social_network.enums.FriendInvitationStatus;
import org.hibernate.Session;
import social_network.primarykey.FriendInvitationPrimaryKey;

import java.util.List;

@Repository
public class FriendInvitationRepository extends AbstractRepository<FriendInvitation, FriendInvitationPrimaryKey> {

    public FriendInvitationRepository(Session session) {
        super(FriendInvitation.class, session);
    }

    public boolean isUsersFriendsById(Integer firstUserId, Integer secondUserId) {

        final String hql = "FROM FriendInvitation f_i WHERE " +
                "((f_i.primaryKey.sender.id = :firstUserId AND f_i.primaryKey.recipient.id = :secondUserId) " +
                "OR (f_i.primaryKey.sender.id = :secondUserId AND f_i.primaryKey.recipient.id = :firstUserId)) " +
                "AND f_i.friendInvitationStatus = 'ACCEPTED'";

        return session.createQuery(hql, FriendInvitation.class)
                .setParameter("firstUserId", firstUserId)
                .setParameter("secondUserId", secondUserId)
                .getSingleResultOrNull() != null;
    }

    public FriendInvitation updateStatus(FriendInvitation friendInvitation, FriendInvitationStatus friendInvitationStatus) {

        friendInvitation.setFriendInvitationStatus(friendInvitationStatus);

        super.update(friendInvitation);

        return friendInvitation;
    }

    public List<FriendInvitation> findSentByUser(User sender) {

        final String hql = "FROM FriendInvitation f_i WHERE f_i.primaryKey.sender = :sender AND f_i.friendInvitationStatus = 'WAITING'";

        return session.createQuery(hql, FriendInvitation.class).setParameter("sender", sender).list();
    }

    public List<FriendInvitation> findReceivedByUserId(User recipient) {

        final String hql = "FROM FriendInvitation f_i WHERE f_i.primaryKey.recipient = :recipient AND f_i.friendInvitationStatus = 'WAITING'";

        return session.createQuery(hql, FriendInvitation.class).setParameter("recipient", recipient).list();
    }

    public FriendInvitation findActiveByUserIds(Integer firstUserId, Integer secondUserId) {

        final String hql = "FROM FriendInvitation f_i WHERE " +
                "((f_i.primaryKey.sender.id = :firstUserId AND f_i.primaryKey.recipient.id = :secondUserId) " +
                "OR (f_i.primaryKey.sender.id = :secondUserId AND f_i.primaryKey.recipient.id = :firstUserId)) " +
                "AND f_i.friendInvitationStatus = 'WAITING'";

        return session.createQuery(hql, FriendInvitation.class)
                .setParameter("firstUserId", firstUserId)
                .setParameter("secondUserId", secondUserId)
                .getSingleResultOrNull();
    }

    public void deleteUserFromFriendByUserIds(Integer userId, Integer friendId) {

        final String hql = "DELETE FriendInvitation f_i WHERE " +
                "((f_i.primaryKey.sender.id = :userId AND f_i.primaryKey.recipient.id = :friendId) " +
                "OR (f_i.primaryKey.sender.id = :friendId AND f_i.primaryKey.recipient.id = :userId)) " +
                "AND f_i.friendInvitationStatus = 'ACCEPTED'";

        session.createQuery(hql).setParameter("userId", userId)
                .setParameter("friendId", friendId)
                .executeUpdate();
    }

    public List<User> findFriendsByUserId(Integer userId) {

        final String hql = "SELECT u FROM User u JOIN FriendInvitation f_i " +
                "ON (u.id = f_i.primaryKey.sender.id OR u.id = f_i.primaryKey.recipient.id) " +
                "WHERE (f_i.primaryKey.sender.id = :userId OR f_i.primaryKey.recipient.id = :userId) " +
                "AND u.id != :userId AND f_i.friendInvitationStatus = 'ACCEPTED'";

        return session.createQuery(hql, User.class)
                .setParameter("userId", userId)
                .list();
    }

}
