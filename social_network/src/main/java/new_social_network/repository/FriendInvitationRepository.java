package new_social_network.repository;

import new_social_network.entity.FriendInvitation;
import new_social_network.entity.User;
import new_social_network.enums.FriendInvitationStatus;
import org.hibernate.Session;

import java.util.List;

public class FriendInvitationRepository extends AbstractRepository<FriendInvitation, Integer> {

    public FriendInvitationRepository(Session session) {
        super(FriendInvitation.class, session);
    }

    public FriendInvitation save(User sender, User recipient, FriendInvitationStatus friendInvitationStatus) {

        FriendInvitation friendInvitation = new FriendInvitation(sender, recipient, FriendInvitationStatus.WAITING);

        super.create(friendInvitation);

        return friendInvitation;
    }

    public FriendInvitation findById(Integer id) {

        return super.findById(id);
    }

    public FriendInvitation findWaitingFriendInvitationBySenderIdAndRecipientId(Integer senderId, Integer recipientId) {

        String hql = "FROM FriendInvitation f WHERE (f.sender.id = :senderId AND f.recipient.id = :recipientId) " +
                "OR (f.recipient.id = :senderId AND f.sender.id = :recipientId) AND f.friendInvitationStatus = 'WAITING'";

        return session.createQuery(hql, FriendInvitation.class)
                .setParameter("senderId", senderId)
                .setParameter("recipientId", recipientId)
                .getSingleResultOrNull();
    }

    public FriendInvitation findBySenderIdAndRecipientId(Integer senderId, Integer recipientId) {

        String hql = "FROM FriendInvitation f WHERE (f.sender.id = :senderId AND f.recipient.id = :recipientId) " +
                "OR (f.recipient.id = :senderId AND f.sender.id = :recipientId)";

        try {

            FriendInvitation friendInvitation = session.createQuery(hql, FriendInvitation.class)
                    .setParameter("senderId", senderId)
                    .setParameter("recipientId", recipientId).getSingleResult();

            return friendInvitation;

        } catch (Exception e) {

            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public FriendInvitation updateStatusById(Integer id, FriendInvitationStatus friendInvitationStatus) {

        FriendInvitation friendInvitation = session.find(FriendInvitation.class, id);

        friendInvitation.setFriendInvitationStatus(friendInvitationStatus);

        super.update(friendInvitation);

        return friendInvitation;
    }

    public List<FriendInvitation> findFriendInvitationUserById(Integer id) {

        String hql = "FROM FriendInvitation f WHERE f.recipient.id = :id AND f.friendInvitationStatus = 'WAITING'";

        return session.createQuery(hql).setParameter("id", id).list();

    }
}
