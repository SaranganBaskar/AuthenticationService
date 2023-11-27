package dev.sarangan.authenticationservice.dtos;

import dev.sarangan.authenticationservice.models.SessionStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateTokenResponseDto {
    private UserDto userDto;

    private SessionStatus status;
}
