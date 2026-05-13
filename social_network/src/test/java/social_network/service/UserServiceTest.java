package social_network.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import social_network.entity.User;
import social_network.exception.EmailAlreadyBusyException;
import social_network.exception.NotFoundException;
import social_network.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock PasswordEncoder passwordEncoder;
    @Mock UserRepository userRepository;
    @InjectMocks UserService userService;

    @Test
    void createUser_shouldEncodePasswordAndSaveUser() {
        when(passwordEncoder.encode("password123")).thenReturn("encoded");
        when(userRepository.create(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.createUser("Ivan", "Ivanov", "ivan@mail.com", "password123");

        assertEquals("encoded", result.getPassword());
        verify(userRepository).create(any(User.class));
    }

    @Test
    void checkingIfEmailOccupied_shouldThrow_whenLoginAlreadyExists() {
        when(userRepository.findByLogin("busy@mail.com")).thenReturn(new User());
        assertThrows(EmailAlreadyBusyException.class, () -> userService.checkingIfEmailOccupied("busy@mail.com"));
    }

    @Test
    void findUserById_shouldThrow_whenUserNotFound() {
        when(userRepository.findById(404)).thenReturn(null);
        assertThrows(NotFoundException.class, () -> userService.findUserById(404));
    }
}
