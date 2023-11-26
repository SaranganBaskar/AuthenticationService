package dev.sarangan.authenticationservice.services;

import dev.sarangan.authenticationservice.exceptions.UserAlreadyExistsException;
import dev.sarangan.authenticationservice.models.Session;
import dev.sarangan.authenticationservice.models.User;
import dev.sarangan.authenticationservice.repositories.SessionRepository;
import dev.sarangan.authenticationservice.repositories.UserRepository;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
    public User signUp(String email, String password) throws UserAlreadyExistsException {
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByEmail(email));
        //Optional<User> optionalUser = Optional.of(userRepository.findByEmail(email));
        if (!optionalUser.isEmpty()) {
            throw new UserAlreadyExistsException("User with " + email + " already exists");
        }

        Date date = new Date();
        User user = new User();
        user.setFullName("User1FirstNameLastName");
        user.setEmail(email);
        user.setPassword(password);
        user.setCreatedAt(date);
        user.setLastUpdatedAt(date);

        //region Act on your data
        User savedUser = userRepository.save(user);
        return savedUser;
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
        Session session = null;
        String newSessionToken = "";
        Optional<User> user = Optional.of(userRepository.findByEmail(email));
        if (user.isPresent()) {

            Map<String, String> claimsMap = new HashMap<>();
            claimsMap.put("userId", user.get().getId().toString());
            claimsMap.put("email", user.get().getEmail());
            claimsMap.put("roles", user.get().getRoles().toString());

            newSessionToken = Jwts.builder().claims(claimsMap).compact();
            //newSessionToken = utilities.generateSessionToken(20);

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
        if (user.isPresent()) {
            Optional<Session> session = Optional.of(sessionRepository.findSessionByUser_IdAndToken(user.get().getId(), authToken));
            if (session.isPresent()) {
                isValidSession = true;
            }
        }
        return isValidSession;
    }

}
