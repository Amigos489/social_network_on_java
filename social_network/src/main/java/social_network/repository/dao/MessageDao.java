package social_network.repository.dao;

import jakarta.persistence.Query;
import org.hibernate.Session;
import social_network.repository.entity.Chat;
import social_network.repository.entity.Message;
import social_network.repository.entity.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class MessageDao {

    private Session session;

    public MessageDao(Session session) {
        this.session = session;
    }

    public void insertMessage(String text, Chat chat, User sender) {

    }
}
