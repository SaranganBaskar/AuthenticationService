package dev.sarangan.authenticationservice.repositories;

import dev.sarangan.authenticationservice.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {

    Session save(Session session);

    Session findSessionByToken(String token);

    Optional<Session> findByUser_IdAndToken(Long userId, String sessionToken);

}
