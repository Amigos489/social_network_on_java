package social_network.repository;

import org.hibernate.Session;
import social_network.entity.CreatorGroupChat;
import social_network.entity.User;
import social_network.primarykey.CreatorGroupChatPrimaryKey;

public class CreatorGroupChatRepository extends AbstractRepository<CreatorGroupChat, CreatorGroupChatPrimaryKey> {

    public CreatorGroupChatRepository(Session session) {
        super(CreatorGroupChat.class, session);
    }

    public User findCreatorGroupChatById(Integer groupChatId) {

        final String hql = "FROM User u JOIN CreatorGroupChat c_g ON u.id = c_g.primaryKey.creator.id WHERE c_g.primaryKey.groupChat.id = :groupChatId";

        return session.createQuery(hql, User.class)
                .setParameter("groupChatId", groupChatId)
                .getSingleResultOrNull();
    }

    public boolean isUserCreatorGroupChatById(Integer userId, Integer groupChatId) {

        User user = findCreatorGroupChatById(groupChatId);

        return user.getId().equals(userId);
    }

}
