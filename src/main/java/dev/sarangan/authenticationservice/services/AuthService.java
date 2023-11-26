package dev.sarangan.authenticationservice.services;

import dev.sarangan.authenticationservice.exceptions.UserAlreadyExistsException;
import dev.sarangan.authenticationservice.models.Session;
import dev.sarangan.authenticationservice.models.User;

public interface AuthService {

    User signUp(String email, String password) throws UserAlreadyExistsException;
    Session login(String email, String password);

    boolean validate(String email, String authToken);
}
