package ivanov.springbootintro.mapper;

import ivanov.springbootintro.config.MapperConfig;
import ivanov.springbootintro.dto.drink.CreateDrinkRequestDto;
import ivanov.springbootintro.dto.drink.DrinkDto;
import ivanov.springbootintro.dto.drink.DrinkDtoWithCategoryNameLikesCount;
import ivanov.springbootintro.dto.drink.DrinkDtoWithCategoryNameListUserLikes;
import ivanov.springbootintro.model.Drink;
import ivanov.springbootintro.model.User;
import java.util.HashSet;
import java.util.Set;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, componentModel = "spring",
        uses = {DrinkCategoryMapper.class, UserMapper.class})
public interface DrinkMapper {

    @Mapping(source = "category", target = "drinkCategory")
    DrinkDto toDto(Drink drink);

    @Mapping(source = "category.name", target = "categoryName")
    DrinkDtoWithCategoryNameLikesCount toDtoWithCategoryNameCounterLikes(Drink drink);

    @Mapping(source = "category.name", target = "categoryName")
    DrinkDtoWithCategoryNameListUserLikes toDtoWithCategoryNameListUserLikes(Drink drink);

    Drink toEntity(CreateDrinkRequestDto requestDto);

    // Метод для підрахунку лайків тільки для DrinkDtoWithCategoryNameCounterLikes
    @AfterMapping
    default void mapLikeCount(
            Drink drink, @MappingTarget DrinkDtoWithCategoryNameLikesCount dto) {
        // Підраховуємо кількість лайків
        Integer likeCount = drink.getLikedByUsers().size();

        // Встановлюємо кількість лайків у DTO
        dto.setCountLikes(likeCount);
    }

    // Метод для маппінгу користувачів на їхні імена (для DrinkDtoWithCategoryNameListUserLikes)
    @AfterMapping
    default void mapLikedByUsers(
            Drink drink, @MappingTarget DrinkDtoWithCategoryNameListUserLikes dto) {
        Set<String> userNames = mapUsersToNames(drink.getLikedByUsers());
        dto.setLikedByUsers(userNames);
    }

    // Метод для маппінгу користувачів на їхні імена
    default Set<String> mapUsersToNames(Set<User> users) {
        Set<String> userNames = new HashSet<>();
        for (User user : users) {
            userNames.add(user.getFirstName()
                    + " "
                    + user.getLastName());
        }
        return userNames;
    }
}
