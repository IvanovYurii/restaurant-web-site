package ivanov.springbootintro.dto.dish;

import java.util.Objects;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DishDtoWithLikesCount extends DishDto {
    private Integer countLikes;

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
        DishDtoWithLikesCount that = (DishDtoWithLikesCount) o;
        return Objects.equals(countLikes, that.countLikes);
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + Objects.hash(countLikes);
    }
}
