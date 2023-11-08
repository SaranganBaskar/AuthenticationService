package dev.sarangan.authenticationservice.services;

import dev.sarangan.authenticationservice.models.Session;

public interface AuthService {
    Session login(String email, String password);

    boolean validate(String email, String authToken);
}
