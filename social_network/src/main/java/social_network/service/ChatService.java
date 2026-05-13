package social_network.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import social_network.dto.GroupChatDto;
import social_network.entity.*;
import social_network.exception.ChatException;
import social_network.exception.NotFoundException;
import social_network.mapper.GroupChatMapper;
import social_network.repository.GroupChatRepository;
import social_network.repository.PersonalChatRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ChatService {

    private final GroupChatMapper groupChatMapper;

    private static Logger logger = LogManager.getLogger(ChatService.class);

    private final String MESSAGE_GROUP_CHAT_NOT_FOUND = "group chat with id = %d not found.";

    private final String MESSAGE_PERSONAL_CHAT_NOT_FOUND = "personal chat between users with id = %d and id = %d not found.";

    private final String MESSAGE_USER_NOT_CREATOR_GROUP_CHAT = "user with id = %d not creator group chat with id = %d.";

    private final String MESSAGE_USER_CREATOR_GROUP_CHAT = "user with id = %d creator group chat with id = %d.";

    private final String MESSAGE_USER_ALREADY_GROUP_CHAT = "User with id = %d is already in a group chat with id = %d.";

    private final String MESSAGE_USER_NOT_GROUP_CHAT = "user with id = %d is not in a group chat with id = %d.";

    private final String MESSAGE_USERS_EMPTY = "list users for add empty.";

    private final PersonalChatRepository personalChatRepository;

    private final GroupChatRepository groupChatRepository;

    public ChatService(GroupChatMapper groupChatMapper, PersonalChatRepository personalChatRepository, GroupChatRepository groupChatRepository) {
        this.groupChatMapper = groupChatMapper;
        this.personalChatRepository = personalChatRepository;
        this.groupChatRepository = groupChatRepository;
    }

    public GroupChatDto createGroupChat(Set<User> users, User creator) {

        logger.info("creating a group chat, creator id = {}.", creator.getId());

        if (!users.stream().anyMatch(item -> creator.getId().equals(item.getId()))) {

            logger.info("the creator with id = {} is not listed for adding to the group chat.", creator.getId());

            users.add(creator);
        }

        if (users.size() <= 1) {

            logger.error((MESSAGE_USERS_EMPTY));

            throw new ChatException(MESSAGE_USERS_EMPTY);
        }

        GroupChat groupChat = groupChatRepository.create(new GroupChat(users, creator));

        for (User user : users) {
            user.getChats().add(groupChat);
        }

        logger.info("group chat with id = {}, adding users = {}, creator id = {} created.", groupChat.getId(), users.toString(), creator.getId());

        return groupChatMapper.doGroupChatDto(groupChat);
    }

    public void deleteGroupChatById(Integer groupChatId, User userWhoDeletes) {

        logger.info(String.format("delete group chat with id = %d, user id who delete = {}.",
                groupChatId,
                userWhoDeletes));

        GroupChat groupChat = findGroupChatById(groupChatId);

        if (!groupChatRepository.isUserCreatorGroupChatById(userWhoDeletes.getId(),
                groupChatId)) {

            logger.error(String.format(MESSAGE_USER_NOT_CREATOR_GROUP_CHAT, userWhoDeletes.getId(), groupChatId));

            throw new ChatException(String.format(MESSAGE_USER_NOT_CREATOR_GROUP_CHAT,
                    userWhoDeletes.getId(),
                    groupChatId));
        }

        for (User user : groupChat.getUsers()) {

            user.getChats().remove(groupChat);
        }

        logger.info(String.format("group chat with id = %d deleted, user id who delete = {}.",
                groupChatId,
                userWhoDeletes));

        groupChatRepository.delete(groupChat);
    }

    public void addUserInGroupChatById(User userForAdd,
                                       Integer chatId,
                                       User userWhoAdds) {

        logger.info("add user with id = {} in group chat with id = {}. user id who add = {}.",
                userForAdd.getId(),
                chatId,
                userWhoAdds.getId());

        GroupChat groupChat = findGroupChatById(chatId);

        if (groupChatRepository.isUserInChatById(chatId, userForAdd.getId())) {

            logger.error(String.format(MESSAGE_USER_ALREADY_GROUP_CHAT,
                    userWhoAdds.getId(),
                    chatId));

            throw new ChatException(String.format(MESSAGE_USER_ALREADY_GROUP_CHAT,
                    userWhoAdds.getId(),
                    chatId));
        }

        groupChat.getUsers().add(userForAdd);

        userForAdd.getChats().add(groupChat);

        logger.info("user with id = {} added in group chat with id = {}. user id who add = {}.",
                userForAdd.getId(),
                chatId,
                userWhoAdds.getId());
    }

    public void kickUserFromGroupChatById(User userForKick,
                                          Integer groupChatId,
                                          User userWhoKick) {

        logger.info("kick user with id {} from group chat with id = {}, user id who kicked = {}.",
                userForKick.getId(),
                groupChatId,
                userWhoKick.getId());

        GroupChat groupChat = findGroupChatById(groupChatId);

        if (!groupChatRepository.isUserCreatorGroupChatById(userWhoKick.getId(),
                groupChatId)) {

            logger.error(String.format(MESSAGE_USER_NOT_CREATOR_GROUP_CHAT,
                    userWhoKick.getId(),
                    groupChatId));

            throw new ChatException(String.format(MESSAGE_USER_NOT_CREATOR_GROUP_CHAT,
                    userWhoKick.getId(),
                    groupChatId));
        }

        if (!groupChatRepository.isUserInChatById(groupChatId,
                userForKick.getId())) {

            logger.error(String.format(MESSAGE_USER_NOT_GROUP_CHAT,
                    userForKick.getId(),
                    groupChatId));

            throw new ChatException(String.format(MESSAGE_USER_NOT_GROUP_CHAT,
                    userForKick.getId(),
                    groupChatId));
        }

        groupChat.getUsers().remove(userForKick);

        userForKick.getChats().remove(groupChat);

        logger.info("user with id {} kicked from group chat with id = {}, user id who kicked = {}.",
                userForKick.getId(),
                groupChatId,
                userWhoKick.getId());
    }

    public void leaveFromGroupChat(Integer groupChatId,
                                   User user) {

        logger.info("user with id = {} leave from group chat with id = {}.",
                user.getId(),
                groupChatId);

        GroupChat groupChat = findGroupChatById(groupChatId);

        if (groupChatRepository.isUserCreatorGroupChatById(user.getId(),
                groupChatId)) {

            logger.error(String.format(MESSAGE_USER_CREATOR_GROUP_CHAT,
                    user.getId(),
                    groupChatId));

            throw new ChatException(String.format(MESSAGE_USER_CREATOR_GROUP_CHAT,
                    user.getId(),
                    groupChatId));
        }

        if (!groupChatRepository.isUserInChatById(groupChatId,
                user.getId())) {

            logger.error(String.format(MESSAGE_USER_NOT_GROUP_CHAT,
                    user.getId(),
                    groupChatId));

            throw new ChatException(String.format(MESSAGE_USER_NOT_GROUP_CHAT,
                    user.getId(),
                    groupChatId));
        }

        groupChat.getUsers().remove(user);

        user.getChats().remove(groupChat);

        logger.info("user with id = {} leaved from group chat with id = {}.",
                user.getId(),
                groupChatId);
    }

    public List<Message> getAllMessageFromGroupChatById(GroupChat chat,
                                                        User user) {

        logger.info("get all messages from group chat with id = {}, user id = {}",
                chat.getId(),
                user.getId());

        if (!groupChatRepository.isUserInChatById(chat.getId(), user.getId())) {

            logger.error(String.format(MESSAGE_USER_NOT_GROUP_CHAT,
                    user.getId(),
                    chat.getId()));

            throw new ChatException(String.format(MESSAGE_USER_NOT_GROUP_CHAT,
                    user.getId(),
                    chat.getId()));
        }

        logger.info("all messages from group chat with id = {}, user id = {}",
                chat.getId(),
                user.getId());

        return groupChatRepository.getAllMessagesById(chat.getId());
    }

    public PersonalChat createPersonalChat(User firstUser,
                                           User secondUser) {

        logger.info("create personal chat for user with id = {} and {}",
                firstUser.getId(),
                secondUser.getId());

        PersonalChat personalChat = new PersonalChat(firstUser,
                secondUser);

        firstUser.getChats().add(personalChat);

        secondUser.getChats().add(personalChat);

        return personalChatRepository.create(personalChat);
    }

    public PersonalChat findPersonalChatByUserIds(User firstUser,
                                                  User secondUser) {

        logger.info("find personal chat by user ids = {} and {}.",
                firstUser.getId(),
                secondUser.getId());

        PersonalChat personalChat = personalChatRepository.findByUserIds(firstUser.getId(),
                secondUser.getId());

        if (personalChat == null) {

            throw new ChatException(String.format(MESSAGE_PERSONAL_CHAT_NOT_FOUND,
                    firstUser.getId(),
                    secondUser.getId()));
        }

        return personalChat;
    }

    public List<Message> getAllMessageFromPersonalChat(User firstUser, User secondUser) {

        logger.info("get all messages from personal chat between users with id = {} and {}.",
                firstUser.getId(),
                secondUser.getId());

        PersonalChat personalChat = personalChatRepository.findByUserIds(firstUser.getId(), secondUser.getId());

        if (personalChat == null) {

            logger.warn("between users with ids = {} and {} not personal chat, so not message.");

            return new ArrayList<>();
        }

        return personalChatRepository.getAllMessagesById(firstUser, secondUser);
    }

    public GroupChat findGroupChatById(Integer chatId) {

        logger.info(String.format("find group chat by id = %d", chatId));

        GroupChat groupChat = groupChatRepository.findById(chatId);

        if (groupChat == null) {

            logger.error(String.format(MESSAGE_GROUP_CHAT_NOT_FOUND, chatId));

            throw new NotFoundException(String.format(MESSAGE_GROUP_CHAT_NOT_FOUND, chatId));
        }

        return groupChat;
    }

    public void sendMessageInGroupChat(GroupChat groupChat, Message message) {

        logger.info("send message in group chat with id = {} user id = {}",
                groupChat.getId(),
                message.getSender().getId());

        if (!checkUserInChatById(groupChat.getId(), message.getSender().getId())) {
            throw new ChatException(String.format(MESSAGE_USER_NOT_GROUP_CHAT,
                    message.getSender().getId(),
                    groupChat.getId()));
        }

        groupChat.getMessages().add(message);
    }

    public boolean checkUserInChatById(Integer chatId, Integer userId) {

        logger.info("check user with id = {} in chat with id = {}.",
                userId,
                chatId);

        return groupChatRepository.isUserInChatById(chatId, userId);
    }
}
