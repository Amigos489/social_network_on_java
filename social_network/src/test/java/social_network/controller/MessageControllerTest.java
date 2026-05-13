package social_network.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import social_network.dto.MessageToUserDto;
import social_network.exception.ChatException;
import social_network.service.CurrentUserService;
import social_network.service.ServiceFacade;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageControllerTest {
    @Mock ServiceFacade serviceFacade;
    @Mock CurrentUserService currentUserService;
    @InjectMocks MessageController controller;

    @Test
    void sendMessageUser_shouldReturnOk_whenPersonalChatExists() {
        MessageToUserDto dto = new MessageToUserDto("hello", 2);
        when(currentUserService.getCurrentUserId()).thenReturn(1);

        ResponseEntity<?> response = controller.sendMessageUser(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(serviceFacade).sendMessageToUser(dto, 1);
    }

    @Test
    void getMessageFromGroupChat_shouldThrow_whenUserIsNotMember() {
        when(currentUserService.getCurrentUserId()).thenReturn(1);
        doThrow(new ChatException("not in chat")).when(serviceFacade).readAllMessagesFromGroupChat(10, 1);

        assertThrows(ChatException.class, () -> controller.getMessageFromGroupChat(10));
    }
}
