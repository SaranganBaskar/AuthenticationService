package dev.sarangan.authenticationservice.controllers;

import dev.sarangan.authenticationservice.dtos.*;
import dev.sarangan.authenticationservice.exceptions.UserAlreadyExistsException;
import dev.sarangan.authenticationservice.exceptions.UserDoesNotExistException;
import dev.sarangan.authenticationservice.models.Session;
import dev.sarangan.authenticationservice.models.SessionStatus;
import dev.sarangan.authenticationservice.models.User;
import dev.sarangan.authenticationservice.services.AuthService;
import jakarta.annotation.security.PermitAll;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto) throws UserAlreadyExistsException {
        User user = authService.signUp(signUpRequestDto.getEmail(), signUpRequestDto.getPassword());
        UserDto userDto = UserDto.from(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) throws UserDoesNotExistException {
        /*A random 20 character string is generated and return to the client. */
        return authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
    }

    @PostMapping("/validate")
    public ResponseEntity<ValidateTokenResponseDto> validate(@RequestBody ValidateTokenRequestDto validateTokenRequestDto) {
        Optional<UserDto> userDto = authService.validate(validateTokenRequestDto.getUserId(), validateTokenRequestDto.getToken());

        ValidateTokenResponseDto validateTokenResponseDto = new ValidateTokenResponseDto();
        if (userDto.isEmpty()) {
            validateTokenResponseDto.setStatus(SessionStatus.INVALID);
            return new ResponseEntity<>(validateTokenResponseDto, HttpStatus.OK);
        }
        validateTokenResponseDto = new ValidateTokenResponseDto();
        validateTokenResponseDto.setUserDto(userDto.get());
        validateTokenResponseDto.setStatus(SessionStatus.ACTIVE);
        return new ResponseEntity<>(validateTokenResponseDto, HttpStatus.OK);
    }
}
