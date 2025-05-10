package ivanov.springbootintro.dto.dish;

import ivanov.springbootintro.dto.dishcategory.DishCategoryDto;
import ivanov.springbootintro.dto.drink.DrinkDtoWithCategoryNameLikesCount;
import ivanov.springbootintro.dto.ingredient.IngredientDto;
import java.util.Objects;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DishDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private DishCategoryDto dishCategory;
    private Set<IngredientDto> ingredients;
    private Integer calories;
    private Integer weight;
    private Boolean available;
    private String imageUrl;
    private Integer preparationTime;
    private Set<DrinkDtoWithCategoryNameLikesCount> suggestedDrinks;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DishDto dishDto = (DishDto) o;
        return Objects.equals(id, dishDto.id)
                && Objects.equals(name, dishDto.name)
                && Objects.equals(price, dishDto.price)
                && Objects.equals(description, dishDto.description)
                && Objects.equals(dishCategory, dishDto.dishCategory)
                && Objects.equals(ingredients, dishDto.ingredients)
                && Objects.equals(calories, dishDto.calories)
                && Objects.equals(weight, dishDto.weight)
                && Objects.equals(available, dishDto.available)
                && Objects.equals(imageUrl, dishDto.imageUrl)
                && Objects.equals(preparationTime, dishDto.preparationTime)
                && Objects.equals(suggestedDrinks, dishDto.suggestedDrinks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, description, dishCategory,
                ingredients, calories, weight, available, imageUrl,
                preparationTime, suggestedDrinks);
    }
}
