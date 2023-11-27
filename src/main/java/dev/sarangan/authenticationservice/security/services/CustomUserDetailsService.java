package dev.sarangan.authenticationservice.security.services;

import dev.sarangan.authenticationservice.models.User;
import dev.sarangan.authenticationservice.repositories.UserRepository;
import dev.sarangan.authenticationservice.security.models.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByEmail(username));

        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException(username + "doesn't exist");
        }
        return new CustomUserDetails(optionalUser.get());
    }
}
