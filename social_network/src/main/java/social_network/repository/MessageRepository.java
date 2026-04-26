package social_network.repository;

import social_network.entity.Chat;
import social_network.entity.Message;
import org.hibernate.Session;
import social_network.entity.User;

import java.util.List;

public class MessageRepository extends AbstractRepository<Message, Integer> {

    public MessageRepository(Session session) {
        super(Message.class, session);
    }

    public List<Message> markReadInChatById(Chat chat, User sender) {

        final String hql = "UPDATE Message m SET m.isRead = true WHERE m.chat = :chat AND m.sender != :sender AND isRead = false";

        session.createQuery(hql).setParameter("chat", chat).setParameter("sender", sender).executeUpdate();

        return session.createQuery("FROM Message AS m WHERE m.chat = :chat", Message.class)
                .setParameter("chat", chat).list();
    }

    public void deleteAllMessageFromChat(Integer chatId) {

        final String hql = "DELETE Message m WHERE m.chat.id = :chatId";

        session.createQuery(hql).setParameter("chatId", chatId).executeUpdate();
    }
}
