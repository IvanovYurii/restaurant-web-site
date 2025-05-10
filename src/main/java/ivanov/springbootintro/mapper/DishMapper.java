package ivanov.springbootintro.mapper;

import ivanov.springbootintro.config.MapperConfig;
import ivanov.springbootintro.dto.dish.CreateDishRequestDto;
import ivanov.springbootintro.dto.dish.DishDto;
import ivanov.springbootintro.dto.dish.DishDtoWithLikesCount;
import ivanov.springbootintro.dto.dish.DishDtoWithUsersLikes;
import ivanov.springbootintro.dto.drink.DrinkDtoWithCategoryNameLikesCount;
import ivanov.springbootintro.dto.ingredient.IngredientDto;
import ivanov.springbootintro.dto.user.UserResponseDto;
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
        uses = {DrinkMapper.class,
                IngredientMapper.class,
                UserMapper.class})
public interface DishMapper {

    // Основне перетворення Dish -> DishDto
    @Mapping(source = "category", target = "dishCategory")
    DishDto toDto(Dish dish);

    // Перетворення Dish -> DishDtoWithLikesCount з підрахунком лайків
    @Mapping(source = "category", target = "dishCategory")
    DishDtoWithLikesCount toDishDtoWithLikesCount(Dish dish);

    // Перетворення Dish -> DishDtoWithUsersLikes з користувачами, які лайкнули
    @Mapping(source = "category", target = "dishCategory")
    DishDtoWithUsersLikes toDishDtoWithUsersLikes(Dish dish);

    // Перетворення CreateDishRequestDto -> Dish
    Dish toEntity(CreateDishRequestDto requestDto);

    // Підрахунок лайків для DishDtoWithLikesCount
    @AfterMapping
    default void mapLikeCount(Dish dish, @MappingTarget DishDtoWithLikesCount dto) {
        dto.setCountLikes(dish.getLikedByUsers().size());
    }

    // Підрахунок користувачів, які лайкнули для DishDtoWithUsersLikes
    @AfterMapping
    default void mapLikedByUsers(Dish dish,
                                 @MappingTarget DishDtoWithUsersLikes dto,
                                 UserMapper userMapper) {
        dto.setLikedByUsers(mapUsersToDto(dish.getLikedByUsers(), userMapper));
    }

    // Мапінг інгредієнтів на відповідні DTO
    @AfterMapping
    default void mapIngredients(Dish dish, @MappingTarget DishDtoWithLikesCount dto,
                                IngredientMapper ingredientMapper) {
        dto.setIngredients(mapIngredientsToDto(dish.getIngredients(), ingredientMapper));
    }

    @AfterMapping
    default void mapIngredients(Dish dish, @MappingTarget DishDtoWithUsersLikes dto,
                                IngredientMapper ingredientMapper) {
        dto.setIngredients(mapIngredientsToDto(dish.getIngredients(), ingredientMapper));
    }

    // Допоміжний метод для перетворення інгредієнтів у IngredientDto
    default Set<IngredientDto> mapIngredientsToDto(Set<Ingredient> ingredients,
                                                   IngredientMapper ingredientMapper) {
        Set<IngredientDto> ingredientDtos = new HashSet<>();
        for (Ingredient ingredient : ingredients) {
            ingredientDtos.add(ingredientMapper.toDto(ingredient));
        }
        return ingredientDtos;
    }

    // Допоміжний метод для перетворення користувачів у UserResponseDto
    default Set<UserResponseDto> mapUsersToDto(Set<User> users, UserMapper userMapper) {
        Set<UserResponseDto> userDtos = new HashSet<>();
        for (User user : users) {
            userDtos.add(userMapper.toDto(user));
        }
        return userDtos;
    }

    // Мапінг рекомендованих напоїв для DishDtoWithLikesCount
    @AfterMapping
    default void mapSuggestedDrinks(Dish dish,
                                    @MappingTarget DishDtoWithLikesCount dto,
                                    DrinkMapper drinkMapper) {
        Set<DrinkDtoWithCategoryNameLikesCount> suggestedDrinks = mapSuggestedDrinksToDto(
                dish.getSuggestedDrinks(), drinkMapper);
        dto.setSuggestedDrinks(suggestedDrinks);
    }

    // Мапінг рекомендованих напоїв для DishDtoWithUsersLikes
    @AfterMapping
    default void mapSuggestedDrinks(Dish dish,
                                    @MappingTarget DishDtoWithUsersLikes dto,
                                    DrinkMapper drinkMapper) {
        Set<DrinkDtoWithCategoryNameLikesCount> suggestedDrinks =
                mapSuggestedDrinksToDto(dish.getSuggestedDrinks(), drinkMapper);
        dto.setSuggestedDrinks(suggestedDrinks);
    }

    // Метод для перетворення рекомендованих напоїв у DTO
    default Set<DrinkDtoWithCategoryNameLikesCount> mapSuggestedDrinksToDto(
            Set<Drink> drinks, DrinkMapper drinkMapper) {
        Set<DrinkDtoWithCategoryNameLikesCount> suggestedDrinksDtos = new HashSet<>();
        for (Drink drink : drinks) {
            suggestedDrinksDtos.add(drinkMapper.toDtoWithCategoryNameCounterLikes(drink));
        }
        return suggestedDrinksDtos;
    }
}
