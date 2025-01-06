package ivanov.springbootintro.mapper;

import ivanov.springbootintro.config.MapperConfig;
import ivanov.springbootintro.dto.ingredient.CreateIngredientRequestDto;
import ivanov.springbootintro.dto.ingredient.IngredientDto;
import ivanov.springbootintro.dto.ingredient.UpdateIngredientRequestDto;
import ivanov.springbootintro.model.Ingredient;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface IngredientMapper {
    IngredientDto toDto(Ingredient ingredient);

    Ingredient toEntity(CreateIngredientRequestDto requestDto);

    Ingredient toEntity(UpdateIngredientRequestDto requestDto);

}
