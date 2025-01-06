package ivanov.springbootintro.service;

import ivanov.springbootintro.dto.ingredient.CreateIngredientRequestDto;
import ivanov.springbootintro.dto.ingredient.IngredientDto;
import ivanov.springbootintro.dto.ingredient.UpdateIngredientRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface IngredientService {
    IngredientDto create(CreateIngredientRequestDto requestDto);

    List<IngredientDto> getAll(Pageable pageable);

    IngredientDto getById(Long id);

    IngredientDto updateById(UpdateIngredientRequestDto requestDto, Long id);

    void deleteById(Long id);
}
