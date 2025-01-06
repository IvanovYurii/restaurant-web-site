package ivanov.springbootintro.dto.drink;

import java.math.BigDecimal;
import java.util.Objects;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DrinkDtoWithCategoryNameLikesCount {

    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private Integer volume;
    private Boolean available;
    private Double alcoholPercentage;
    private String categoryName;
    private Integer calories;
    private Integer countLikes;
    private String imageUrl;

    @Override
    public boolean equals(Object o) {
        if (this
                == o) {
            return true;
        }
        if (o
                == null
                || getClass()
                != o.getClass()) {
            return false;
        }
        DrinkDtoWithCategoryNameLikesCount that = (DrinkDtoWithCategoryNameLikesCount) o;
        return Objects.equals(id, that.id)
                && Objects.equals(name, that.name)
                && Objects.equals(price, that.price)
                && Objects.equals(description, that.description)
                && Objects.equals(volume, that.volume)
                && Objects.equals(available, that.available)
                && Objects.equals(alcoholPercentage, that.alcoholPercentage)
                && Objects.equals(categoryName, that.categoryName)
                && Objects.equals(calories, that.calories)
                && Objects.equals(countLikes, that.countLikes)
                && Objects.equals(imageUrl, that.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, description, volume, available,
                alcoholPercentage, categoryName, calories, countLikes, imageUrl);
    }
}
