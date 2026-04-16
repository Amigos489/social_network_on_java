package social_network.repository.dao;

import org.hibernate.Session;
import social_network.repository.entity.Chat;
import social_network.repository.entity.Message;
import social_network.repository.entity.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ChatDao {

    private Session session;

    public ChatDao(Session session) {
        this.session = session;
    }

    public Chat createNewChat(List<User> users) {

        List<Message> messages = new ArrayList<>();

        Chat chat = new Chat(LocalDate.now(), users, messages);

        return chat;
    }
}
