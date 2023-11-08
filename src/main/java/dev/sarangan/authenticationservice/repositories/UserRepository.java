package dev.sarangan.authenticationservice.repositories;

import dev.sarangan.authenticationservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User save(User user);

    User findByEmail(String email);


}
