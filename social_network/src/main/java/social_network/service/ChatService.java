package social_network.Service;

import social_network.entity.*;
import social_network.exception.GroupChatNotFoundException;
import social_network.exception.community.ErrorAddingException;
import social_network.exception.community.ErrorDeletingException;
import social_network.primarykey.CreatorGroupChatPrimaryKey;
import social_network.repository.CreatorGroupChatRepository;
import social_network.repository.GroupChatRepository;
import social_network.repository.PersonalChatRepository;

import java.util.ArrayList;
import java.util.List;

public class ChatService {

    private final PersonalChatRepository personalChatRepository;

    private final GroupChatRepository groupChatRepository;

    private final CreatorGroupChatRepository creatorGroupChatRepository;

    public ChatService(PersonalChatRepository personalChatRepository, GroupChatRepository groupChatRepository, CreatorGroupChatRepository creatorGroupChatRepository) {
        this.personalChatRepository = personalChatRepository;
        this.groupChatRepository = groupChatRepository;
        this.creatorGroupChatRepository = creatorGroupChatRepository;
    }

    public GroupChat createGroupChat(List<User> users, User creator) {

        users.add(creator);

        GroupChat groupChat = groupChatRepository.create(new GroupChat(users));

        for (User user : users) {
            user.getChats().add(groupChat);
        }

        CreatorGroupChat creatorGroupChat = new CreatorGroupChat(new CreatorGroupChatPrimaryKey(creator, groupChat));

        creatorGroupChat = creatorGroupChatRepository.create(creatorGroupChat);

        return groupChat;
    }

    public void deleteGroupChatById(Integer groupChatId, User userWhoDeletes) {

        GroupChat groupChat = groupChatRepository.findById(groupChatId);

        if (!creatorGroupChatRepository.isUserCreatorGroupChatById(userWhoDeletes.getId(), groupChatId)) {
            throw new ErrorDeletingException(String.format("User with id = %d cannot delete a group chat with id = %d", userWhoDeletes.getId(), groupChatId));
        }

        if (groupChat == null) {
            throw new GroupChatNotFoundException(groupChatId);
        }

        for (User user : groupChat.getUsers()) {

            user.getChats().remove(groupChat);
        }

        CreatorGroupChat creatorGroupChat = creatorGroupChatRepository.findById(new CreatorGroupChatPrimaryKey(userWhoDeletes, groupChat));

        creatorGroupChatRepository.delete(creatorGroupChat);

        groupChatRepository.delete(groupChat);
    }

    public List<User> addUserInGroupChatById(User userForAdd, Integer groupChatId, User userWhoAdds) {

        if (groupChatRepository.isUserInChatById(groupChatId, userForAdd.getId())) {

            throw new ErrorAddingException(String.format("User with id = %d is already in a chat with id = %d", userWhoAdds.getId(), groupChatId));
        }

        GroupChat groupChat = groupChatRepository.findById(groupChatId);

        groupChat.getUsers().add(userForAdd);

        userForAdd.getChats().add(groupChat);

        return groupChat.getUsers();
    }

    public List<User> kickUserFromGroupChatById(User userForDelete, Integer groupChatId, User userWhoDeletes) {

        //Проверка, что пользователь, который удаляет создатель чата.
        if (!creatorGroupChatRepository.isUserCreatorGroupChatById(userWhoDeletes.getId(), groupChatId)) {

            throw new ErrorDeletingException(String.format("User with id = %d cannot kick user from group chat with id = %d", userWhoDeletes.getId(), groupChatId));
        }

        //Проверка, что пользователь, которого удаляют находится в чате.
        if (groupChatRepository.isUserInChatById(groupChatId, userForDelete.getId())) {

            throw new ErrorDeletingException(String.format(String.format("User with id = %d not in the chat with id = %d", userForDelete.getId(), groupChatId), userWhoDeletes.getId(), groupChatId));
        }

        GroupChat groupChat = groupChatRepository.findById(groupChatId);

        groupChat.getUsers().remove(userForDelete);

        userForDelete.getChats().remove(groupChat);

        return groupChat.getUsers();
    }

    public List<User> leaveFromGroupChat(Integer groupChatId, User user) {

        //Проверка, что пользователь, который удаляет создатель чата.
        if (creatorGroupChatRepository.isUserCreatorGroupChatById(user.getId(), groupChatId)) {

            throw new ErrorDeletingException(String.format("User with id = %d cannot leave from group chat with id = %d", user.getId(), groupChatId));
        }

        if (!groupChatRepository.isUserInChatById(groupChatId, user.getId())) {

            throw new ErrorDeletingException(String.format("user with id = %d is not in a chat with id = %d", user.getId(), groupChatId));
        }

        GroupChat groupChat = groupChatRepository.findById(groupChatId);

        groupChat.getUsers().remove(user);

        user.getChats().remove(groupChat);

        return groupChat.getUsers();
    }

    public List<Message> getAllMessageFromGroupChatById(GroupChat chat, User user) {

        if (groupChatRepository.isUserInChatById(chat.getId(), user.getId())) {

            throw new ErrorDeletingException(String.format("User with id = %d not in the chat with id = %d", user.getId(), chat.getId()));
        }

        return groupChatRepository.getAllMessagesById(chat.getId());
    }

    public PersonalChat createPersonalChat(User firstUser, User secondUser) {

        PersonalChat personalChat = new PersonalChat(firstUser, secondUser);

        firstUser.getChats().add(personalChat);

        secondUser.getChats().add(personalChat);

        return personalChatRepository.create(personalChat);
    }

    public PersonalChat findPersonalChatByUserIds(User firstUser, User secondUser) {

        return personalChatRepository.findByUserIds(firstUser.getId(), secondUser.getId());
    }

    public List<Message> getAllMessageFromPersonalChat(User firstUser, User secondUser) {

        PersonalChat personalChat = personalChatRepository.findByUserIds(firstUser.getId(), secondUser.getId());

        if (personalChat == null) {
            return new ArrayList<>();
        }

        return personalChatRepository.getAllMessagesById(firstUser, secondUser);
    }

    public GroupChat findGroupChatById(Integer chatId) {

        GroupChat groupChat = groupChatRepository.findById(chatId);

        if (groupChat == null) {
            throw new GroupChatNotFoundException(chatId);
        }

        return groupChat;
    }

    public boolean checkUserInChatById(Integer chatId, Integer userId) {
        return groupChatRepository.isUserInChatById(chatId, userId);
    }
}
