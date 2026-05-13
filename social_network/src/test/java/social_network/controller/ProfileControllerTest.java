package social_network.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import social_network.exception.NotFoundException;
import social_network.service.CurrentUserService;
import social_network.service.ServiceFacade;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileControllerTest {
    @Mock ServiceFacade serviceFacade;
    @Mock CurrentUserService currentUserService;
    @InjectMocks
    ProfileController controller;

    @Test
    void setAgeInProfile_shouldReturnOk_whenAgeIsValid() {
        when(currentUserService.getCurrentUserId()).thenReturn(1);

        ResponseEntity<?> response = controller.setAgeInProfile(20);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(serviceFacade).setAgeInProfile(1, 20);
    }

    @Test
    void findById_shouldThrow_whenProfileNotFound() {
        when(serviceFacade.findProfile(404)).thenThrow(new NotFoundException("not found"));

        assertThrows(NotFoundException.class, () -> controller.findById(404));
    }
}
