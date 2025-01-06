package ivanov.springbootintro.dto.dish;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DishDtoWithDishNamesLikesCount {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String categoryName;
    private Set<String> ingredients = new HashSet<>();
    private Integer calories;
    private Integer weight;
    private Boolean available;
    private String imageUrl;
    private Integer preparationTime;
    private Integer countLikes;
    private Set<String> suggestedDrinks = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DishDtoWithDishNamesLikesCount that = (DishDtoWithDishNamesLikesCount) o;
        return Objects.equals(id, that.id)
                && Objects.equals(name, that.name)
                && Objects.equals(price, that.price)
                && Objects.equals(description, that.description)
                && Objects.equals(categoryName, that.categoryName)
                && Objects.equals(ingredients, that.ingredients)
                && Objects.equals(calories, that.calories)
                && Objects.equals(weight, that.weight)
                && Objects.equals(available, that.available)
                && Objects.equals(imageUrl, that.imageUrl)
                && Objects.equals(preparationTime, that.preparationTime)
                && Objects.equals(countLikes, that.countLikes)
                && Objects.equals(suggestedDrinks, that.suggestedDrinks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, description, categoryName, ingredients, calories,
                weight, available, imageUrl, preparationTime, countLikes, suggestedDrinks);
    }
}
