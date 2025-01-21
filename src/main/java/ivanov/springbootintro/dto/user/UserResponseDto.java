package ivanov.springbootintro.dto.user;

import ivanov.springbootintro.model.Role;
import java.util.Set;

public record UserResponseDto(
        Long id,
        String email,
        String firstName,
        String lastName,
        String avatarUrl,
        Set<Role> roles) {
}
