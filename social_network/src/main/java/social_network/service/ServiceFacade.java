package social_network.service;

import social_network.repository.dao.ChatDao;
import social_network.repository.dao.MessageDao;
import social_network.repository.dao.UserDao;
import social_network.repository.entity.Chat;
import social_network.repository.entity.Message;
import social_network.repository.entity.User;

import java.util.ArrayList;
import java.util.List;

public class ServiceFacade {

    private UserDao userDao;
    private MessageDao messageDao;
    private ChatDao chatDao;

    public ServiceFacade(UserDao userDao, MessageDao messageDao, ChatDao chatDao) {
        this.userDao = userDao;
        this.messageDao = messageDao;
        this.chatDao = chatDao;
    }

    public void sendMessage(Integer senderId, Integer recipientId, String text) {

    }

    public void findMessagesFromChatById(Integer id) {

    }

}
