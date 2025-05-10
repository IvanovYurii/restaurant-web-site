package ivanov.springbootintro.service;

import ivanov.springbootintro.dto.drinkcategory.CreateDrinkCategoryRequestDto;
import ivanov.springbootintro.dto.drinkcategory.DrinkCategoryDto;
import ivanov.springbootintro.dto.drinkcategory.UpdateDrinkCategoryRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface DrinkCategoryService {
    DrinkCategoryDto create(CreateDrinkCategoryRequestDto requestDto);

    List<DrinkCategoryDto> getAll(Pageable pageable);

    DrinkCategoryDto getById(Long id);

    DrinkCategoryDto updateById(UpdateDrinkCategoryRequestDto requestDto, Long id);

    void deleteById(Long id);
}
