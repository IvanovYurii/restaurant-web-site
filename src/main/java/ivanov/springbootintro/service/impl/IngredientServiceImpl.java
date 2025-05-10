package ivanov.springbootintro.service.impl;

import ivanov.springbootintro.dto.ingredient.CreateIngredientRequestDto;
import ivanov.springbootintro.dto.ingredient.IngredientDto;
import ivanov.springbootintro.dto.ingredient.UpdateIngredientRequestDto;
import ivanov.springbootintro.exception.EntityNotFoundException;
import ivanov.springbootintro.mapper.IngredientMapper;
import ivanov.springbootintro.model.Ingredient;
import ivanov.springbootintro.repository.ingredient.IngredientRepository;
import ivanov.springbootintro.service.IngredientService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;

    @Override
    public IngredientDto create(CreateIngredientRequestDto requestDto) {
        Ingredient ingredient = ingredientMapper.toEntity(requestDto);
        return ingredientMapper.toDto(ingredientRepository.save(ingredient));
    }

    @Override
    public List<IngredientDto> getAll(Pageable pageable) {
        return ingredientRepository.findAll(pageable).stream()
                .map(ingredientMapper::toDto)
                .toList();
    }

    @Override
    public IngredientDto getById(Long id) {
        Ingredient ingredient = assertIngredientExistsById(id);
        return ingredientMapper.toDto(ingredient);
    }

    @Override
    public IngredientDto updateById(UpdateIngredientRequestDto requestDto, Long id) {
        assertIngredientExistsById(id);
        Ingredient ingredient = ingredientMapper.toEntity(requestDto);
        ingredient.setId(id);
        return ingredientMapper.toDto(ingredientRepository.save(ingredient));
    }

    @Override
    public void deleteById(Long id) {
        assertIngredientExistsById(id);
        ingredientRepository.deleteById(id);
    }

    private Ingredient assertIngredientExistsById(Long id) {
        return ingredientRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find ingredient by id=" + id));
    }
}
