package ivanov.springbootintro.mapper;

import ivanov.springbootintro.config.MapperConfig;
import ivanov.springbootintro.dto.dish.CreateDishRequestDto;
import ivanov.springbootintro.dto.dish.DishDto;
import ivanov.springbootintro.dto.dish.DishDtoWithDishNamesLikesCount;
import ivanov.springbootintro.dto.dish.DishDtoWithDishNamesUsersLikes;
import ivanov.springbootintro.dto.drink.DrinkDto;
import ivanov.springbootintro.model.Dish;
import ivanov.springbootintro.model.Drink;
import ivanov.springbootintro.model.Ingredient;
import ivanov.springbootintro.model.User;
import java.util.HashSet;
import java.util.Set;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class,
        uses = {DishMapper.class, Ingredient.class, UserMapper.class})
public interface DishMapper {

    @Mapping(source = "category", target = "dishCategory")
    DishDto toDto(Dish dish);

    Dish toEntity(CreateDishRequestDto requestDto);

    @Mapping(source = "category.name", target = "categoryName")
    DishDtoWithDishNamesLikesCount toDishDtoWithDishNamesCountLikes(Dish dish);

    @Mapping(source = "category.name", target = "categoryName")
    DishDtoWithDishNamesUsersLikes toDishDtoWithDishNamesUsersLikes(Dish dish);

    // Метод для підрахунку лайків тільки для DishDtoWithDishNamesLikesCount
    @AfterMapping
    default void mapLikeCount(Dish dish, @MappingTarget DishDtoWithDishNamesLikesCount dto) {
        // Підраховуємо кількість лайків
        Integer likeCount = dish.getLikedByUsers().size();
        // Встановлюємо кількість лайків у DTO
        dto.setCountLikes(likeCount);
    }

    // Метод для маппінгу користувачів на їхні імена (для DrinkDtoWithCategoryNameListUserLikes)
    @AfterMapping
    default void mapLikedByUsers(Dish dish, @MappingTarget DishDtoWithDishNamesUsersLikes dto) {
        Set<String> userNames = mapUsersToNames(dish.getLikedByUsers());
        dto.setLikedByUsers(userNames);
    }

    // Метод для маппінгу користувачів на їхні імена
    default Set<String> mapUsersToNames(Set<User> users) {
        Set<String> userNames = new HashSet<>();
        for (User user : users) {
            userNames.add(user.getFirstName() + " " + user.getLastName());
        }
        return userNames;
    }

    @AfterMapping
    default void mapIngredients(Dish dish, @MappingTarget DishDtoWithDishNamesLikesCount dto) {
        Set<String> ingredients = mapIngredientsToName(dish.getIngredients());
        dto.setIngredients(ingredients);
    }

    @AfterMapping
    default void mapIngredients(Dish dish, @MappingTarget DishDtoWithDishNamesUsersLikes dto) {
        Set<String> ingredients = mapIngredientsToName(dish.getIngredients());
        dto.setIngredients(ingredients);
    }

    default Set<String> mapIngredientsToName(Set<Ingredient> ingredients) {
        Set<String> ingredientNames = new HashSet<>();
        for (Ingredient ingredient : ingredients) {
            ingredientNames.add(ingredient.getName());
        }
        return ingredientNames;
    }

   /* @AfterMapping
    default void mapSuggestedDrinks(Dish dish, @MappingTarget DishDtoWithDishNamesLikesCount dto) {
        Set<DrinkDto> suggestedDrinks = mapSuggestedDrinksToName(dish.getSuggestedDrinks());
        dto.setSuggestedDrinks(suggestedDrinks);
    }*/

    /*@AfterMapping
    default void mapSuggestedDrinks(Dish dish, @MappingTarget DishDtoWithDishNamesUsersLikes dto) {
        Set<String> ingredients = mapSuggestedDrinksToName(dish.getSuggestedDrinks());
        dto.setSuggestedDrinks(ingredients);
    }*/

    default Set<String> mapSuggestedDrinksToName(Set<Drink> drinks) {
        Set<String> suggestedDrinksNames = new HashSet<>();
        for (Drink drink : drinks) {
            suggestedDrinksNames.add(drink.getName());
        }
        return suggestedDrinksNames;
    }
}
