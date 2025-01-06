package ivanov.springbootintro.service;

import ivanov.springbootintro.dto.dishcategory.CreateDishCategoryRequestDto;
import ivanov.springbootintro.dto.dishcategory.DishCategoryDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface DishCategoryService {
    DishCategoryDto create(CreateDishCategoryRequestDto requestDto);

    List<DishCategoryDto> getAll(Pageable pageable);

    DishCategoryDto getById(Long id);

    DishCategoryDto updateById(CreateDishCategoryRequestDto requestDto, Long id);

    void deleteById(Long id);
}
