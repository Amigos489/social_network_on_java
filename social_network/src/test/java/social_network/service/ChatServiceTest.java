package social_network.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import social_network.dto.GroupChatDto;
import social_network.entity.GroupChat;
import social_network.entity.User;
import social_network.exception.ChatException;
import social_network.mapper.GroupChatMapper;
import social_network.repository.GroupChatRepository;
import social_network.repository.PersonalChatRepository;
import social_network.TestData;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatServiceTest {
    @Mock
    GroupChatMapper groupChatMapper;
    @Mock
    PersonalChatRepository personalChatRepository;
    @Mock
    GroupChatRepository groupChatRepository;
    @InjectMocks
    ChatService chatService;

    @Test
    void createGroupChat_shouldAddCreatorAndReturnDto_whenUsersEnough() {
        User creator = TestData.user(1);
        User member = TestData.user(2);
        Set<User> users = new HashSet<>(Set.of(member));
        GroupChat saved = TestData.groupChat(10, users, creator);
        GroupChatDto dto = new GroupChatDto(10, null, Set.of());
        when(groupChatRepository.create(any(GroupChat.class))).thenReturn(saved);
        when(groupChatMapper.doGroupChatDto(saved)).thenReturn(dto);

        GroupChatDto result = chatService.createGroupChat(users, creator);

        assertSame(dto, result);
        assertTrue(users.contains(creator));
    }

    @Test
    void createGroupChat_shouldThrow_whenOnlyCreatorSpecified() {
        User creator = TestData.user(1);
        Set<User> users = new HashSet<>(Set.of(creator));

        assertThrows(ChatException.class, () -> chatService.createGroupChat(users, creator));
        verify(groupChatRepository, never()).create(any());
    }
}
