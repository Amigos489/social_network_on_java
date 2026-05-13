package social_network.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import social_network.dto.MessageDto;
import social_network.entity.Chat;
import social_network.entity.Message;
import social_network.entity.User;
import social_network.mapper.MessageMapper;
import social_network.repository.MessageRepository;

import java.util.List;

@Service
public class MessageService {

    private static Logger logger = LogManager.getLogger(MessageService.class);

    private final MessageMapper messageMapper;

    private MessageRepository messageRepository;

    public MessageService(MessageMapper messageMapper, MessageRepository messageRepository) {
        this.messageMapper = messageMapper;
        this.messageRepository = messageRepository;
    }

    public Message createMessage(String text, User sender, Chat chat) {

        logger.info("create message.");

        Message message = new Message(text, sender, chat);

        return messageRepository.create(message);
    }

    public List<MessageDto> markMessageReadInChatById(Chat chat, User user) {

        logger.info(String.format("mark message read in chat with id = %d.", chat.getId()));

        List<Message> message = messageRepository.markReadInChatById(chat, user);
        return messageMapper.doMessageDtoList(message);
    }

    public void deleteAllMessageFromChat(Integer chatId) {

        logger.info(String.format("delete all message from chat with id = %d", chatId));

        messageRepository.deleteAllMessageFromChat(chatId);
    }
}
