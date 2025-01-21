package ivanov.springbootintro.dto.dish;

import ivanov.springbootintro.dto.user.UserResponseDto;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DishDtoWithUsersLikes extends DishDto {
    private Set<UserResponseDto> likedByUsers = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        DishDtoWithUsersLikes that = (DishDtoWithUsersLikes) o;
        return Objects.equals(likedByUsers, that.likedByUsers);
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + Objects.hash(likedByUsers);
    }
}
