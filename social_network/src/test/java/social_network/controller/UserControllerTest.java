package social_network.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import social_network.dto.AuthenticationDto;
import social_network.dto.JwtResponse;
import social_network.dto.UserRegisterDto;
import social_network.service.JwtService;
import social_network.service.ServiceFacade;
import social_network.service.UserDetailsServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock ServiceFacade serviceFacade;
    @Mock AuthenticationManager authenticationManager;
    @Mock JwtService jwtService;
    @Mock UserDetailsServiceImpl userDetailsService;
    @Mock Authentication authentication;
    @Mock UserDetails userDetails;
    @InjectMocks
    UserController controller;

    @Test
    void registerUser_shouldReturnOk_whenDtoIsValid() {
        UserRegisterDto dto = new UserRegisterDto("Ivan", "Ivanov", "ivan@mail.com", "password123");

        ResponseEntity<?> response = controller.registerUser(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(serviceFacade).registerUser(dto);
    }

    @Test
    void authenticateUser_shouldReturnUnauthorized_whenPasswordIsWrong() {
        AuthenticationDto dto = new AuthenticationDto("ivan@mail.com", "bad-password");
        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("bad"));

        ResponseEntity<?> response = controller.authenticateUser(dto);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void authenticateUser_shouldReturnToken_whenCredentialsAreValid() {
        AuthenticationDto dto = new AuthenticationDto("ivan@mail.com", "password123");
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(userDetailsService.loadUserByUsername("ivan@mail.com")).thenReturn(userDetails);
        when(jwtService.generateToken(userDetails)).thenReturn("jwt-token");

        ResponseEntity<?> response = controller.authenticateUser(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("jwt-token", ((JwtResponse) response.getBody()).token());
    }
}
