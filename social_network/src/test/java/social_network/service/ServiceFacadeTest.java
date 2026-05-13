package social_network.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import social_network.dto.CommunityInfoDto;
import social_network.dto.UserRegisterDto;
import social_network.entity.Profile;
import social_network.entity.User;
import social_network.exception.UserBlockedException;
import social_network.TestData;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceFacadeTest {
    @Mock
    UserService userService;
    @Mock
    ProfileService profileService;
    @Mock
    MessageService messageService;
    @Mock
    ChatService chatService;
    @Mock
    FriendInvitationService friendInvitationService;
    @Mock
    PostService postService;
    @Mock
    CommunityService communityService;
    @InjectMocks
    ServiceFacade serviceFacade;

    @Test
    void registerUser_shouldCheckEmailCreateUserAndCreateProfile() {
        UserRegisterDto dto = new UserRegisterDto("Ivan", "Ivanov", "ivan@mail.com", "password123");
        User user = TestData.user(1);
        when(userService.createUser("Ivan", "Ivanov", "ivan@mail.com", "password123")).thenReturn(user);
        when(profileService.createProfile(user)).thenReturn(new Profile(user));

        serviceFacade.registerUser(dto);

        verify(userService).checkingIfEmailOccupied("ivan@mail.com");
        verify(userService).createUser("Ivan", "Ivanov", "ivan@mail.com", "password123");
        verify(profileService).createProfile(user);
    }

    @Test
    void createCommunity_shouldThrow_whenCurrentUserBlocked() {
        User blocked = TestData.blockedUser(1);
        when(userService.findUserById(1)).thenReturn(blocked);

        assertThrows(UserBlockedException.class,
                () -> serviceFacade.createCommunity(new CommunityInfoDto("Java", "desc"), 1));
        verify(communityService, never()).createCommunity(any(), any(), any());
    }
}
