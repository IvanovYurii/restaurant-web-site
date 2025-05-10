package ivanov.springbootintro.dto.drink;

import ivanov.springbootintro.dto.user.UserResponseDto;
import java.util.Objects;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DrinkDtoWithCategoryNameListUserLikes extends DrinkDto {
    private Set<UserResponseDto> likedByUsers;

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
        DrinkDtoWithCategoryNameListUserLikes that = (DrinkDtoWithCategoryNameListUserLikes) o;
        return Objects.equals(likedByUsers, that.likedByUsers);
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + (likedByUsers != null ? likedByUsers.hashCode() : 0);
    }
}
