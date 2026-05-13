package social_network.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import social_network.exception.NotFoundException;
import social_network.repository.AdminRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {
    @Mock
    AdminRepository adminRepository;
    @InjectMocks
    AdminService adminService;

    @Test
    void blockUser_shouldDelegateToRepository() {
        adminService.blockUser(1);
        verify(adminRepository).blockUserById(1);
    }

    @Test
    void deleteCommunity_shouldThrow_whenRepositoryThrows() {
        doThrow(new NotFoundException("community not found")).when(adminRepository).deleteCommunityById(404);
        assertThrows(NotFoundException.class, () -> adminService.deleteCommunity(404));
    }
}
