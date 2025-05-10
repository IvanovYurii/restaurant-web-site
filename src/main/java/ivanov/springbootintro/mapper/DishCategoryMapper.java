package ivanov.springbootintro.mapper;

import ivanov.springbootintro.config.MapperConfig;
import ivanov.springbootintro.dto.dishcategory.CreateDishCategoryRequestDto;
import ivanov.springbootintro.dto.dishcategory.DishCategoryDto;
import ivanov.springbootintro.model.DishCategory;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface DishCategoryMapper {
    DishCategoryDto toDto(DishCategory dishCategory);

    DishCategory toEntity(CreateDishCategoryRequestDto requestDto);
}
