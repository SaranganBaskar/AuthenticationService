package dev.sarangan.authenticationservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    private String email;
    private String fullName;
    private String sessionToken;

}
