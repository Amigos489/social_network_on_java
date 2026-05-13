package social_network.repository;

import org.springframework.stereotype.Repository;
import social_network.entity.Message;
import social_network.entity.PersonalChat;
import org.hibernate.Session;
import social_network.entity.User;

import java.util.List;

@Repository
public class PersonalChatRepository extends AbstractRepository<PersonalChat, Integer> {

    public PersonalChatRepository(Session session) {
        super(PersonalChat.class, session);
    }

    public PersonalChat findByUserIds(Integer firstUserId, Integer secondUserId) {

        String hql = "FROM PersonalChat pc WHERE (pc.firstUser.id = :firstId AND pc.secondUser.id = :secondId) " +
                "OR (pc.firstUser.id = :secondId AND pc.secondUser.id = :firstId)";

        return session.createQuery(hql, PersonalChat.class)
                .setParameter("firstId", firstUserId)
                .setParameter("secondId", secondUserId)
                .getSingleResultOrNull();
    }

    public List<Message> getAllMessagesById(User firstUser, User secondUser) {

        PersonalChat personalChat = findByUserIds(firstUser.getId(), secondUser.getId());

        return personalChat.getMessages();
    }
}
