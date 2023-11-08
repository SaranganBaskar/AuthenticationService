package dev.sarangan.authenticationservice.controllers;

import dev.sarangan.authenticationservice.dtos.LoginRequestDto;
import dev.sarangan.authenticationservice.dtos.LoginResponseDto;
import dev.sarangan.authenticationservice.dtos.ValidateTokenRequestDto;
import dev.sarangan.authenticationservice.dtos.ValidateTokenResponseDto;
import dev.sarangan.authenticationservice.models.Session;
import dev.sarangan.authenticationservice.services.AuthService;
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

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        /*A random 20 character string is generated and return to the client. */
        LoginResponseDto loginResponseDto = null;
        Optional<Session> generatedSession = Optional.of(authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword()));
        if (generatedSession.isPresent()) {
            loginResponseDto= new LoginResponseDto();
            loginResponseDto.setEmail(generatedSession.get().getUser().getEmail());
            loginResponseDto.setFullName(generatedSession.get().getUser().getFullName());
            loginResponseDto.setSessionToken(generatedSession.get().getToken());
        }

        return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
    }

    @PostMapping("/validate")
    public ResponseEntity<ValidateTokenResponseDto> validate(@RequestBody ValidateTokenRequestDto validateTokenRequestDto) {
        boolean isSessionValid = authService.validate(validateTokenRequestDto.getEmail(), validateTokenRequestDto.getToken());
        String message = isSessionValid
                ?
                "Success!! Your user token is valid :-) ":
                "Oops!! Your user token is Invalid :-(";
        ValidateTokenResponseDto validateTokenResponseDto = new ValidateTokenResponseDto();
        validateTokenResponseDto.setMessage(message);
        return new ResponseEntity<>(validateTokenResponseDto, HttpStatus.OK);
    }
}
