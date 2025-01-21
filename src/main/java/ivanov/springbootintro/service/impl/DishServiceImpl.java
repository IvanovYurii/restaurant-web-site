package ivanov.springbootintro.service.impl;

import ivanov.springbootintro.dto.dish.CreateDishRequestDto;
import ivanov.springbootintro.dto.dish.DishDtoWithLikesCount;
import ivanov.springbootintro.dto.dish.DishDtoWithUsersLikes;
import ivanov.springbootintro.exception.EntityNotFoundException;
import ivanov.springbootintro.mapper.DishMapper;
import ivanov.springbootintro.model.Dish;
import ivanov.springbootintro.model.DishCategory;
import ivanov.springbootintro.model.Drink;
import ivanov.springbootintro.model.Ingredient;
import ivanov.springbootintro.repository.dish.DishRepository;
import ivanov.springbootintro.repository.dishcategory.DishCategoryRepository;
import ivanov.springbootintro.repository.drink.DrinkRepository;
import ivanov.springbootintro.repository.ingredient.IngredientRepository;
import ivanov.springbootintro.service.DishService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class DishServiceImpl implements DishService {
    private final DishRepository dishRepository;
    private final DishCategoryRepository dishCategoryRepository;
    private final DishMapper dishMapper;
    private final DrinkRepository drinkRepository;
    private final IngredientRepository ingredientRepository;

    @Override
    public DishDtoWithLikesCount create(
            CreateDishRequestDto requestDto) {
        Dish dish = dishMapper.toEntity(requestDto);
        dish.setCategory(validateCategoryIdsExistence(requestDto.categoryId()));
        updateDishRelations(dish, requestDto);
        return dishMapper.toDishDtoWithLikesCount(dishRepository.save(dish));
    }

    @Override
    public List<DishDtoWithLikesCount> getAll(
            Pageable pageable) {
        return dishRepository.findAll(pageable)
                .stream()
                .map(dishMapper::toDishDtoWithLikesCount)
                .toList();
    }

    @Override
    public DishDtoWithUsersLikes getById(Long id) {
        Dish dish = assertDishExistsById(id);
        return dishMapper.toDishDtoWithUsersLikes(dish);
    }

    @Override
    public DishDtoWithUsersLikes updateById(
            CreateDishRequestDto requestDto, Long id) {
        Dish dish = assertDishExistsById(id);

        if (requestDto.name() != null && !requestDto.name().isEmpty()) {
            dish.setName(requestDto.name());
        }

        if (requestDto.price() != null && requestDto.price() > 0) {
            dish.setPrice(requestDto.price());
        }

        if (requestDto.description() != null && !requestDto.description().isEmpty()) {
            dish.setDescription(requestDto.description());
        }

        if (requestDto.weight() != null && requestDto.weight() > 0) {
            dish.setWeight(requestDto.weight());
        }

        if (requestDto.calories() != null && requestDto.calories() > 0) {
            dish.setCalories(requestDto.calories());
        }

        if (requestDto.imageUrl() != null && !requestDto.imageUrl().isEmpty()) {
            dish.setImageUrl(requestDto.imageUrl());
        }

        if (requestDto.preparationTime() != null && requestDto.preparationTime() > 0) {
            dish.setPreparationTime(requestDto.preparationTime());
        }

        if (requestDto.available() != null) {
            dish.setAvailable(requestDto.available());
        }

        if (requestDto.categoryId() != null && requestDto.categoryId() > 0) {
            dish.setCategory(validateCategoryIdsExistence(requestDto.categoryId()));
        }

        updateDishRelations(dish, requestDto);

        return dishMapper.toDishDtoWithUsersLikes(dishRepository.save(dish));
    }

    @Override
    public void deleteById(Long id) {
        assertDishExistsById(id);
        dishRepository.deleteById(id);
    }

    @Override
    public List<DishDtoWithLikesCount> getDishesByDishCategoryId(
            Long id, Pageable pageable) {
        return dishRepository.findDishByCategory_Id(id).stream()
                .map(dishMapper::toDishDtoWithLikesCount)
                .toList();
    }

    private Dish assertDishExistsById(Long id) {
        return dishRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find dish by id=" + id));
    }

    private DishCategory validateCategoryIdsExistence(Long dishCategoryId) {
        return dishCategoryRepository.findById(dishCategoryId)
                .orElseThrow(() -> new EntityNotFoundException("Dish category by ID="
                        + dishCategoryId + " not found"));
    }

    private Set<Ingredient> mapIngredientIdsToEntities(Set<Long> ingredientIds) {
        Set<Ingredient> ingredients = new HashSet<>();
        if (ingredientIds != null) {
            for (Long ingredientId : ingredientIds) {
                Ingredient ingredient = ingredientRepository.findById(ingredientId)
                        .orElseThrow(() -> new EntityNotFoundException("Ingredient with ID="
                                + ingredientId + " not found"));
                ingredients.add(ingredient);
            }
        }
        return ingredients;
    }

    private Set<Drink> mapSuggestedDrinkIdsToEntities(Set<Long> suggestedDrinkIds) {
        Set<Drink> drinks = new HashSet<>();
        if (suggestedDrinkIds != null) {
            for (Long drinkId : suggestedDrinkIds) {
                Drink drink = drinkRepository.findById(drinkId)
                        .orElseThrow(() -> new EntityNotFoundException("Drink with ID="
                                + drinkId + " not found"));
                drinks.add(drink);
            }
        }
        return drinks;
    }

    private void updateDishRelations(Dish dish, CreateDishRequestDto requestDto) {
        if (requestDto.ingredientIds() != null && !requestDto.ingredientIds().isEmpty()) {
            dish.setIngredients(mapIngredientIdsToEntities(requestDto.ingredientIds()));
        }

        if (requestDto.suggestedDrinkIds() != null && !requestDto.suggestedDrinkIds().isEmpty()) {
            dish.setSuggestedDrinks(mapSuggestedDrinkIdsToEntities(
                    requestDto.suggestedDrinkIds())
            );
        }
    }
}
