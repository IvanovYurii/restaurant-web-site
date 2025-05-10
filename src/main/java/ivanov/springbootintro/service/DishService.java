package ivanov.springbootintro.service;

import ivanov.springbootintro.dto.dish.CreateDishRequestDto;
import ivanov.springbootintro.dto.dish.DishDtoWithLikesCount;
import ivanov.springbootintro.dto.dish.DishDtoWithUsersLikes;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface DishService {
    DishDtoWithLikesCount create(
            CreateDishRequestDto requestDto);

    List<DishDtoWithLikesCount> getAll(
            Pageable pageable);

    DishDtoWithUsersLikes getById(Long id);

    DishDtoWithUsersLikes updateById(
            CreateDishRequestDto requestDto, Long id);

    void deleteById(Long id);

    List<DishDtoWithLikesCount> getDishesByDishCategoryId(
            Long id, Pageable pageable);
}
