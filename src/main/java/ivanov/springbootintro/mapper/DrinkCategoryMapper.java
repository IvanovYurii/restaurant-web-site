package ivanov.springbootintro.mapper;

import ivanov.springbootintro.config.MapperConfig;
import ivanov.springbootintro.dto.drinkcategory.CreateDrinkCategoryRequestDto;
import ivanov.springbootintro.dto.drinkcategory.DrinkCategoryDto;
import ivanov.springbootintro.dto.drinkcategory.UpdateDrinkCategoryRequestDto;
import ivanov.springbootintro.model.DrinkCategory;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface DrinkCategoryMapper {
    DrinkCategoryDto toDto(DrinkCategory drinkCategory);

    DrinkCategory toEntity(CreateDrinkCategoryRequestDto requestDto);

    DrinkCategory toEntity(UpdateDrinkCategoryRequestDto requestDto);
}
