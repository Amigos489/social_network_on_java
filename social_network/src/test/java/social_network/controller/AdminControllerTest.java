package social_network.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import social_network.exception.NotFoundException;
import social_network.service.AdminService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {
    @Mock AdminService adminService;
    @InjectMocks AdminController adminController;

    @Test
    void blockUser_shouldReturnOk_whenUserExists() {
        ResponseEntity<?> response = adminController.blockUser(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(adminService).blockUser(1);
    }

    @Test
    void blockUser_shouldThrow_whenServiceThrows() {
        doThrow(new NotFoundException("not found")).when(adminService).blockUser(404);
        assertThrows(NotFoundException.class, () -> adminController.blockUser(404));
    }
}
