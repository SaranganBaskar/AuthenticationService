package dev.sarangan.authenticationservice.repositories;

import dev.sarangan.authenticationservice.models.Session;
import dev.sarangan.authenticationservice.models.User;
import dev.sarangan.authenticationservice.services.Utilities;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SessionRepositoryTest {

    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void createSessionTest(){

        //region Arrange
        String tokenTest = Utilities.generateSessionToken(20);
        User user = userRepository.findByEmail("user1@gmail.com");
        Date sessionDate = new Date();
        Session session = new Session();
        session.setUser(user);
        session.setToken(tokenTest);
        session.setCreatedAt(sessionDate);
        session.setLastUpdatedAt(sessionDate);
        //endregion

        //region Act
        Session newlyCreatedSessionTest = sessionRepository.save(session);
        //endregion

        //region Assert
        assertThat(newlyCreatedSessionTest.getUser())
                .isEqualTo(user);

        assertThat(newlyCreatedSessionTest.getToken()).isNotEmpty();
        //endregion

    }

    @Test
    void findSessionByToken() {
        Optional<Session> session = sessionRepository.findByUser_IdAndToken(1L,"iaNTFUHrOgJSN8R7ix41");
        assertThat(session)
                .isNotNull();

    }
}