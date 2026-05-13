package social_network.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import social_network.exception.FriendInvitationException;
import social_network.service.CurrentUserService;
import social_network.service.ServiceFacade;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FriendInvitationControllerTest {
    @Mock ServiceFacade serviceFacade;
    @Mock CurrentUserService currentUserService;
    @InjectMocks FriendInvitationController controller;

    @Test
    void sendFriendInvitation_shouldReturnOk_whenUsersAreNotFriends() {
        when(currentUserService.getCurrentUserId()).thenReturn(1);
        ResponseEntity<?> response = controller.sendFriendInvitation(2);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(serviceFacade).sendFriendInvitation(1, 2);
    }

    @Test
    void acceptFriendInvitation_shouldThrow_whenInvitationDoesNotExist() {
        when(currentUserService.getCurrentUserId()).thenReturn(2);
        doThrow(new FriendInvitationException("not found")).when(serviceFacade).acceptFriendInvitation(1, 2, 2);
        assertThrows(FriendInvitationException.class, () -> controller.acceptFriendInvitation(1));
    }
}
