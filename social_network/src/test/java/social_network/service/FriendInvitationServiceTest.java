package social_network.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import social_network.entity.FriendInvitation;
import social_network.entity.User;
import social_network.enums.FriendInvitationStatus;
import social_network.exception.NotFoundException;
import social_network.mapper.FriendInvitationMapper;
import social_network.primarykey.FriendInvitationPrimaryKey;
import social_network.repository.FriendInvitationRepository;
import social_network.TestData;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FriendInvitationServiceTest {
    @Mock
    FriendInvitationMapper friendInvitationMapper;
    @Mock
    FriendInvitationRepository repository;
    @InjectMocks
    FriendInvitationService service;

    @Test
    void createFriendInvitation_shouldSaveWaitingInvitation() {
        when(repository.create(any(FriendInvitation.class))).thenAnswer(invocation -> invocation.getArgument(0));

        FriendInvitation result = service.createFriendInvitation(TestData.user(1), TestData.user(2));

        assertEquals(FriendInvitationStatus.WAITING, result.getFriendInvitationStatus());
        verify(repository).create(any(FriendInvitation.class));
    }

    @Test
    void acceptFriendInvitationById_shouldThrow_whenInvitationNotFound() {
        User sender = TestData.user(1);
        User recipient = TestData.user(2);
        when(repository.findById(any(FriendInvitationPrimaryKey.class))).thenReturn(null);

        assertThrows(NotFoundException.class, () -> service.acceptFriendInvitationById(sender, recipient));
    }
}
