package dev.sarangan.authenticationservice.services;

import dev.sarangan.authenticationservice.dtos.LoginResponseDto;
import dev.sarangan.authenticationservice.dtos.UserDto;
import dev.sarangan.authenticationservice.exceptions.UserAlreadyExistsException;
import dev.sarangan.authenticationservice.exceptions.UserDoesNotExistException;
import dev.sarangan.authenticationservice.models.Session;
import dev.sarangan.authenticationservice.models.SessionStatus;
import dev.sarangan.authenticationservice.models.User;
import dev.sarangan.authenticationservice.repositories.SessionRepository;
import dev.sarangan.authenticationservice.repositories.UserRepository;

import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, SessionRepository sessionRepository, Utilities utilities, PasswordEncoder passwordEncoder) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
        this.utilities = utilities;
        this.passwordEncoder = passwordEncoder;
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
        user.setPassword(passwordEncoder.encode(password));
        user.setCreatedAt(date);
        user.setLastUpdatedAt(date);

        //region Act on your data
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    @Override
    public ResponseEntity<LoginResponseDto> login(String email, String password) throws UserDoesNotExistException {
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
        LoginResponseDto responseDto = new LoginResponseDto();

        if (user.isEmpty()) {
            throw new UserDoesNotExistException("User Does not exist");
        }

        if (!passwordEncoder.matches(password, user.get().getPassword())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

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
        sessionToCreate.setSessionStatus(SessionStatus.ACTIVE);
        session = sessionRepository.save(sessionToCreate);

        MultiValueMapAdapter<String, String> responseHeadersMap = new MultiValueMapAdapter<>(new HashMap<>());
        responseHeadersMap.add("AUTH_TOKEN", session.getToken());

        UserDto userDto = UserDto.from(user.get());
        responseDto.setUserDto(userDto);

        return new ResponseEntity<>(responseDto, responseHeadersMap, HttpStatus.OK);
    }

    @Override
    public Optional<UserDto> validate(Long userId, String authToken) {

        Optional<Session> sessionOptional = sessionRepository.findByUser_IdAndToken(userId, authToken);
        if (sessionOptional.isEmpty()) {
            return Optional.empty();
        }

        Session session = sessionOptional.get();

        if (!session.getSessionStatus().equals(SessionStatus.ACTIVE)) {
            return Optional.empty();
        }

        User user = userRepository.findById(userId).get();
        UserDto userDto = UserDto.from(user);

        return Optional.of(userDto);
    }

}
