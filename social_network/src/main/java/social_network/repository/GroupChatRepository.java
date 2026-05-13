package social_network.repository;

import org.springframework.stereotype.Repository;
import social_network.entity.*;
import org.hibernate.Session;

import java.util.List;
import java.util.Set;

@Repository
public class GroupChatRepository extends AbstractRepository<GroupChat, Integer> {

    public GroupChatRepository(Session session) {
        super(GroupChat.class, session);
    }

    public Set<User> addUserById(Integer id, User user) {

        GroupChat groupChat = findById(id);

        Set<User> users = groupChat.getUsers();
        users.add(user);

        Set<Chat> chats = user.getChats();
        chats.add(groupChat);

        return users;
    }

    public Set<User> deleteUserById(Integer chatId, User user) {

        GroupChat groupChat = findById(chatId);

        Set<User> users = groupChat.getUsers();
        users.remove(user);

        Set<Chat> chats = user.getChats();
        chats.remove(groupChat);

        return users;
    }

    public List<Message> getAllMessagesById(Integer id) {

        GroupChat groupChat  = findById(id);

        return groupChat.getMessages();
    }

    public boolean isUserInChatById(Integer chatId, Integer userId) {

        String sql = "SELECT COUNT(*) FROM user_chat WHERE chats_id = :chatId AND users_id = :userId";

        long countRow = session.createNativeQuery(sql, Long.class)
                .setParameter("chatId", chatId)
                .setParameter("userId", userId)
                .getSingleResultOrNull();

        return countRow == 1;
    }

    public User findCreatorGroupChatById(Integer groupChatId) {

        GroupChat groupChat = findById(groupChatId);

        return groupChat.getCreator();
    }

    public boolean isUserCreatorGroupChatById(Integer userId, Integer groupChatId) {

        User user = findCreatorGroupChatById(groupChatId);

        return user.getId().equals(userId);
    }
}
