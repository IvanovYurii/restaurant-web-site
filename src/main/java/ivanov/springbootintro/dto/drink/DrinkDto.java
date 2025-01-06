package ivanov.springbootintro.dto.drink;

import ivanov.springbootintro.dto.drinkcategory.DrinkCategoryDto;
import ivanov.springbootintro.model.User;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DrinkDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private Integer volume;
    private DrinkCategoryDto drinkCategory;
    private Boolean available;
    private Double alcoholPercentage;
    private Integer calories;
    private Set<User> likedByUsers;
    private String imageUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DrinkDto drinkDto = (DrinkDto) o;
        return Objects.equals(id, drinkDto.id)
                && Objects.equals(name, drinkDto.name)
                && Objects.equals(price, drinkDto.price)
                && Objects.equals(description, drinkDto.description)
                && Objects.equals(volume, drinkDto.volume)
                && Objects.equals(drinkCategory, drinkDto.drinkCategory)
                && Objects.equals(available, drinkDto.available)
                && Objects.equals(alcoholPercentage, drinkDto.alcoholPercentage)
                && Objects.equals(calories, drinkDto.calories)
                && Objects.equals(imageUrl, drinkDto.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, description, volume, drinkCategory, available,
                alcoholPercentage, calories, imageUrl);
    }
}
