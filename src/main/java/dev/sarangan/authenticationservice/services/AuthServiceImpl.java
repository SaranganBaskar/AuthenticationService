package dev.sarangan.authenticationservice.services;

import dev.sarangan.authenticationservice.models.Session;
import dev.sarangan.authenticationservice.models.User;
import dev.sarangan.authenticationservice.repositories.SessionRepository;
import dev.sarangan.authenticationservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;

    private SessionRepository sessionRepository;

    private Utilities utilities;

    public AuthServiceImpl(UserRepository userRepository, SessionRepository sessionRepository, Utilities utilities) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
        this.utilities = utilities;
    }

    @Override
    public Session login(String email, String password) {
        /*
         * From the parameter (email), Check if the user exist in DB
         * IF User exists,
         *  Create a session (i.e., Create session token in the table = "Session"
         * ELSE user doesn't exist, Then return empty session
         *
         * */
        Session session= null;
        String newSessionToken = "";
        Optional<User> user = Optional.of(userRepository.findByEmail(email));
        if (user.isPresent()) {
            newSessionToken = utilities.generateSessionToken(20);

            Date sessionDate = new Date();
            Session sessionToCreate = new Session();
            sessionToCreate.setUser(user.get());
            sessionToCreate.setCreatedAt(sessionDate);
            sessionToCreate.setLastUpdatedAt(sessionDate);
            sessionToCreate.setToken(newSessionToken);
            session = sessionRepository.save(sessionToCreate);
        }

        return session;
    }

    @Override
    public boolean validate(String email, String authToken) {
        boolean isValidSession = false;
        Optional<User> user = Optional.of(userRepository.findByEmail(email));
        if(user.isPresent()){
            Optional<Session> session= Optional.of(sessionRepository.findSessionByUser_IdAndToken(user.get().getId(), authToken));
            if(session.isPresent()){
                isValidSession = true;
            }
        }
        return isValidSession;
    }

}
