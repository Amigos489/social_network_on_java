package social_network.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import social_network.entity.User;
import social_network.exception.NotFoundException;
import social_network.repository.UserRepository;

@Service
public class CurrentUserService {

    private final UserRepository userRepository;

    public CurrentUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User is not authenticated");
        }

        String username = authentication.getName();

        User user = userRepository.findByLogin(username);

        if (user == null) {
            throw new NotFoundException(String.format("user with login = %s not found", username));
        }

        return user;
    }

    public Integer getCurrentUserId() {
        return getCurrentUser().getId();
    }
}
