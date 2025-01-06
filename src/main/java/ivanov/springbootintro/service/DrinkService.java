package ivanov.springbootintro.service;

import ivanov.springbootintro.dto.drink.CreateDrinkRequestDto;
import ivanov.springbootintro.dto.drink.DrinkDto;
import ivanov.springbootintro.dto.drink.DrinkDtoWithCategoryNameLikesCount;
import ivanov.springbootintro.dto.drink.DrinkDtoWithCategoryNameListUserLikes;
import ivanov.springbootintro.dto.drink.UpdateDrinkRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface DrinkService {
    DrinkDto create(CreateDrinkRequestDto requestDto);

    List<DrinkDtoWithCategoryNameLikesCount> getAll(Pageable pageable);

    DrinkDtoWithCategoryNameListUserLikes getById(Long id);

    DrinkDto updateById(UpdateDrinkRequestDto requestDto, Long id);

    void deleteById(Long id);

    List<DrinkDtoWithCategoryNameLikesCount> getDrinksByDrinkCategoryId(
            Long id, Pageable pageable
    );
}
