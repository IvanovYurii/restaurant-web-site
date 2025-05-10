package ivanov.springbootintro.mapper;

import ivanov.springbootintro.config.MapperConfig;
import ivanov.springbootintro.dto.drink.CreateDrinkRequestDto;
import ivanov.springbootintro.dto.drink.DrinkDto;
import ivanov.springbootintro.dto.drink.DrinkDtoWithCategoryNameLikesCount;
import ivanov.springbootintro.dto.drink.DrinkDtoWithCategoryNameListUserLikes;
import ivanov.springbootintro.dto.user.UserResponseDto;
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

    @Mapping(source = "category", target = "drinkCategory")
    DrinkDtoWithCategoryNameLikesCount toDtoWithCategoryNameCounterLikes(Drink drink);

    @Mapping(source = "category", target = "drinkCategory")
    DrinkDtoWithCategoryNameListUserLikes toDtoWithCategoryNameListUserLikes(Drink drink);

    Drink toEntity(CreateDrinkRequestDto requestDto);

    // Метод для підрахунку лайків тільки для DrinkDtoWithCategoryNameCounterLikes
    @AfterMapping
    default void mapLikeCount(Drink drink, @MappingTarget DrinkDtoWithCategoryNameLikesCount dto) {
        Integer likeCount = (drink.getLikedByUsers() != null) ? drink.getLikedByUsers().size() : 0;
        dto.setCountLikes(likeCount);
    }

    // Метод для маппінгу користувачів на їхні DTO (для DrinkDtoWithCategoryNameListUserLikes)
    @AfterMapping
    default void mapLikedByUsers(Drink drink,
                                 @MappingTarget DrinkDtoWithCategoryNameListUserLikes dto) {
        Set<UserResponseDto> userDtos = mapUsersToUserDtos(drink.getLikedByUsers());
        dto.setLikedByUsers(userDtos);
    }

    // Метод для маппінгу користувачів на їхні DTO (UserResponseDto)
    default Set<UserResponseDto> mapUsersToUserDtos(Set<User> users) {
        Set<UserResponseDto> userDtos = new HashSet<>();
        if (users != null) {
            for (User user : users) {
                // Перетворюємо кожного користувача в UserResponseDto
                UserResponseDto userResponseDto = new UserResponseDto(user.getId(),
                        user.getEmail(), user.getFirstName(), user.getLastName(),
                        user.getAvatarUrl(), user.getRoles());
                userDtos.add(userResponseDto);
            }
        }
        return userDtos;
    }
}
