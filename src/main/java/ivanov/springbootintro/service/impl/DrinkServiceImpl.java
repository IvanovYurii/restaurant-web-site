package ivanov.springbootintro.service.impl;

import ivanov.springbootintro.dto.drink.CreateDrinkRequestDto;
import ivanov.springbootintro.dto.drink.DrinkDto;
import ivanov.springbootintro.dto.drink.DrinkDtoWithCategoryNameLikesCount;
import ivanov.springbootintro.dto.drink.DrinkDtoWithCategoryNameListUserLikes;
import ivanov.springbootintro.dto.drink.UpdateDrinkRequestDto;
import ivanov.springbootintro.exception.EntityNotFoundException;
import ivanov.springbootintro.mapper.DrinkMapper;
import ivanov.springbootintro.model.Drink;
import ivanov.springbootintro.model.DrinkCategory;
import ivanov.springbootintro.repository.drink.DrinkRepository;
import ivanov.springbootintro.repository.drinkcategory.DrinkCategoryRepository;
import ivanov.springbootintro.service.DrinkService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DrinkServiceImpl implements DrinkService {
    private final DrinkRepository drinkRepository;
    private final DrinkCategoryRepository drinkCategoryRepository;
    private final DrinkMapper drinkMapper;

    @Override
    public DrinkDto create(CreateDrinkRequestDto requestDto) {
        Drink drink = drinkMapper.toEntity(requestDto);
        drink.setCategory(validateCategoryIdsExistence(requestDto.categoryId()));
        return drinkMapper.toDto(drinkRepository.save(drink));
    }

    @Override
    public List<DrinkDtoWithCategoryNameLikesCount> getAll(Pageable pageable) {
        return drinkRepository.findAll(pageable)
                .stream()
                .map(drinkMapper::toDtoWithCategoryNameCounterLikes)
                .toList();
    }

    @Override
    public DrinkDtoWithCategoryNameListUserLikes getById(Long id) {
        Drink drink = assertDrinkExistsById(id);
        return drinkMapper.toDtoWithCategoryNameListUserLikes(drink);
    }

    @Override
    public DrinkDto updateById(UpdateDrinkRequestDto requestDto, Long id) {
        Drink drink = assertDrinkExistsById(id);
        if (requestDto.name() != null && !requestDto.name().isEmpty()) {
            drink.setName(requestDto.name());
        }
        if (requestDto.price() != null && requestDto.price().intValue() > 0) {
            drink.setPrice(requestDto.price());
        }
        if (requestDto.description() != null && !requestDto.description().isEmpty()) {
            drink.setDescription(requestDto.description());
        }
        if (requestDto.volume() != null && requestDto.volume() > 0) {
            drink.setVolume(requestDto.volume());
        }
        if (requestDto.categoryId() != null && requestDto.categoryId() > 0) {
            drink.setCategory(validateCategoryIdsExistence(requestDto.categoryId()));
        }
        if (requestDto.available() != null) {
            drink.setAvailable(requestDto.available());
        }
        if (requestDto.alcoholPercentage() != null
                && requestDto.alcoholPercentage().intValue() > 0) {
            drink.setAlcoholPercentage(requestDto.alcoholPercentage());
        }
        if (requestDto.calories() != null && requestDto.calories() > 0) {
            drink.setCalories(requestDto.calories());
        }
        if (requestDto.imageUrl() != null && !requestDto.imageUrl().isEmpty()) {
            drink.setImageUrl(requestDto.imageUrl());
        }

        return drinkMapper.toDto(drinkRepository.save(drink));
    }

    @Override
    public void deleteById(Long id) {
        assertDrinkExistsById(id);
        drinkRepository.deleteById(id);
    }

    @Override
    public List<DrinkDtoWithCategoryNameLikesCount> getDrinksByDrinkCategoryId(
            Long id, Pageable pageable) {
        return drinkRepository.findAllByCategory_Id(id).stream()
                .map(drinkMapper::toDtoWithCategoryNameCounterLikes)
                .toList();
    }

    private Drink assertDrinkExistsById(Long id) {
        return drinkRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find drink by id=" + id));
    }

    private DrinkCategory validateCategoryIdsExistence(Long drinkCategoryId) {
        return drinkCategoryRepository.findById(drinkCategoryId)
                .orElseThrow(() -> new EntityNotFoundException("Drink category by ID="
                        + drinkCategoryId + " not found"));
    }
}
