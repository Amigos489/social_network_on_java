package social_network.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import social_network.dto.CreateGroupChatDto;
import social_network.dto.GroupChatDto;
import social_network.exception.ChatException;
import social_network.service.CurrentUserService;
import social_network.service.ServiceFacade;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatControllerTest {
    @Mock ServiceFacade serviceFacade;
    @Mock CurrentUserService currentUserService;
    @InjectMocks ChatController chatController;

    @Test
    void createGroupChat_shouldReturnCreatedChat_whenDtoIsValid() {
        CreateGroupChatDto dto = new CreateGroupChatDto(1, List.of(1, 2));
        GroupChatDto expected = new GroupChatDto(10, null, java.util.Set.of());
        when(serviceFacade.createGroupChat(dto)).thenReturn(expected);

        ResponseEntity<?> response = chatController.createGroupChat(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(expected, response.getBody());
    }

    @Test
    void deleteGroupChat_shouldThrow_whenCurrentUserIsNotCreator() {
        when(currentUserService.getCurrentUserId()).thenReturn(2);
        doThrow(new ChatException("not creator")).when(serviceFacade).deleteGroupChat(10, 2);

        assertThrows(ChatException.class, () -> chatController.deleteGroupChat(10));
    }
}
