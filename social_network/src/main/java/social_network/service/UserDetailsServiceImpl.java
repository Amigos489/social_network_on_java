package social_network.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import social_network.entity.User;
import social_network.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        User user = userRepository.findByLogin(login);

        if (user != null) {

            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getLogin())
                    .password(user.getPassword())
                    .roles(String.valueOf(user.getRole()))
                    .build();
        } else {
            throw new UsernameNotFoundException(login);
        }
    }


}
