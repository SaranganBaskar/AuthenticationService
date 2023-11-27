package dev.sarangan.authenticationservice.services;

import dev.sarangan.authenticationservice.dtos.LoginResponseDto;
import dev.sarangan.authenticationservice.dtos.UserDto;
import dev.sarangan.authenticationservice.exceptions.UserAlreadyExistsException;
import dev.sarangan.authenticationservice.exceptions.UserDoesNotExistException;
import dev.sarangan.authenticationservice.models.Session;
import dev.sarangan.authenticationservice.models.User;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface AuthService {

    User signUp(String email, String password) throws UserAlreadyExistsException;
    ResponseEntity<LoginResponseDto> login(String email, String password) throws UserDoesNotExistException;

    Optional<UserDto> validate(Long userId, String authToken);
}
