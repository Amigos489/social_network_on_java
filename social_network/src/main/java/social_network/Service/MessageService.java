package social_network.Service;

import social_network.entity.Chat;
import social_network.entity.Message;
import social_network.entity.User;
import social_network.repository.MessageRepository;

import java.util.List;

public class MessageService {

    private MessageRepository messageRepository;
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message createMessage(String text, User sender, Chat chat) {

        Message message = new Message(text, sender, chat);

        return messageRepository.create(message);
    }

    public List<Message> markMessageReadInChatById(Chat chat, User user) {

        return messageRepository.markReadInChatById(chat, user);
    }

    public void deleteAllMessageFromGroupChat(Integer chatId) {

        messageRepository.deleteAllMessageFromChat(chatId);
    }
}
