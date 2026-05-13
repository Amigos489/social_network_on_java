package social_network.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import social_network.entity.Chat;
import social_network.entity.Message;
import social_network.mapper.MessageMapper;
import social_network.repository.MessageRepository;
import social_network.TestData;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {
    @Mock
    MessageMapper messageMapper;
    @Mock
    MessageRepository messageRepository;
    @InjectMocks
    MessageService messageService;

    @Test
    void createMessage_shouldSaveMessageInChat() {
        Chat chat = new Chat();
        when(messageRepository.create(any(Message.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Message result = messageService.createMessage("hello", TestData.user(1), chat);

        assertEquals("hello", result.getText());
        assertSame(chat, result.getChat());
    }

    @Test
    void markMessageReadInChatById_shouldPropagateRepositoryFailure() {
        Chat chat = new Chat();
        RuntimeException exception = new RuntimeException("db error");
        when(messageRepository.markReadInChatById(eq(chat), any())).thenThrow(exception);

        assertSame(exception, assertThrows(RuntimeException.class, () -> messageService.markMessageReadInChatById(chat, TestData.user(1))));
    }
}
