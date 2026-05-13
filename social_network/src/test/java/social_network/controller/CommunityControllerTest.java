package social_network.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import social_network.dto.CommunityInfoDto;
import social_network.dto.CreatePostInCommunityDto;
import social_network.exception.CommunityException;
import social_network.service.CurrentUserService;
import social_network.service.ServiceFacade;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommunityControllerTest {
    @Mock ServiceFacade serviceFacade;
    @Mock CurrentUserService currentUserService;
    @InjectMocks CommunityController communityController;

    @Test
    void createCommunity_shouldReturnCreatedCommunity_whenCurrentUserIsAllowed() {
        CommunityInfoDto request = new CommunityInfoDto("Java", "Spring community");
        CommunityInfoDto expected = new CommunityInfoDto("Java", "Spring community");
        when(currentUserService.getCurrentUserId()).thenReturn(1);
        when(serviceFacade.createCommunity(request, 1)).thenReturn(expected);

        ResponseEntity<?> response = communityController.createCommunity(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(expected, response.getBody());
    }

    @Test
    void joinCommunity_shouldThrow_whenUserAlreadyJoined() {
        when(currentUserService.getCurrentUserId()).thenReturn(1);
        doThrow(new CommunityException("already joined")).when(serviceFacade).joinCommunity(5, 1);

        assertThrows(CommunityException.class, () -> communityController.joinCommunity(5));
    }
}
