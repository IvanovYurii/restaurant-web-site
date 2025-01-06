package ivanov.springbootintro.service;

import ivanov.springbootintro.dto.dish.CreateDishRequestDto;
import ivanov.springbootintro.dto.dish.DishDtoWithDishNamesLikesCount;
import ivanov.springbootintro.dto.dish.DishDtoWithDishNamesUsersLikes;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface DishService {
    DishDtoWithDishNamesLikesCount create(
            CreateDishRequestDto requestDto);

    List<DishDtoWithDishNamesLikesCount> getAll(
            Pageable pageable);

    DishDtoWithDishNamesUsersLikes getById(Long id);

    DishDtoWithDishNamesUsersLikes updateById(
            CreateDishRequestDto requestDto, Long id);

    void deleteById(Long id);

    List<DishDtoWithDishNamesLikesCount> getDishesByDishCategoryId(
            Long id, Pageable pageable);
}
