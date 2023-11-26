package dev.sarangan.authenticationservice.dtos;

import dev.sarangan.authenticationservice.models.Role;
import dev.sarangan.authenticationservice.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserDto {
    private String email;
    private Set<Role> roleSet = new HashSet<>();

    public static UserDto from(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setRoleSet(user.getRoles());
        return userDto;
    }
}
