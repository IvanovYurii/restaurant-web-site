package ivanov.springbootintro.dto.dish;

import ivanov.springbootintro.dto.dishcategory.DishCategoryDto;
import ivanov.springbootintro.dto.drink.DrinkDto;
import ivanov.springbootintro.dto.ingredient.IngredientDto;
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
    private Integer likes;
    private Set<DrinkDto> suggestedDrinks;
}
