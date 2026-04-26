package social_network.repository;

import social_network.entity.*;
import org.hibernate.Session;

import java.util.List;

public class GroupChatRepository extends AbstractRepository<GroupChat, Integer> {

    public GroupChatRepository(Session session) {
        super(GroupChat.class, session);
    }



    public List<User> addUserById(Integer id, User user) {

        GroupChat groupChat = findById(id);

        List<User> users = groupChat.getUsers();
        users.add(user);

        List<Chat> chats = user.getChats();
        chats.add(groupChat);

        return users;
    }

    public List<User> deleteUserById(Integer chatId, User user) {

        GroupChat groupChat = findById(chatId);

        List<User> users = groupChat.getUsers();
        users.remove(user);

        List<Chat> chats = user.getChats();
        chats.remove(groupChat);

        return users;
    }

    public List<Message> getAllMessagesById(Integer id) {

        GroupChat groupChat  = findById(id);

        return groupChat.getMessages();
    }

    public boolean isUserInChatById(Integer chatId, Integer userId) {

        String sql = "SELECT COUNT(*) FROM users_chat WHERE chats_id = :chatId AND users_id = :userId";

        long countRow = session.createNativeQuery(sql, Long.class)
                .setParameter("chatId", chatId)
                .setParameter("userId", userId)
                .getSingleResultOrNull();

        return countRow == 1;
    }
}
