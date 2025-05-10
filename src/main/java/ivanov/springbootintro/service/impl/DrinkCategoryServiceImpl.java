package ivanov.springbootintro.service.impl;

import ivanov.springbootintro.dto.drinkcategory.CreateDrinkCategoryRequestDto;
import ivanov.springbootintro.dto.drinkcategory.DrinkCategoryDto;
import ivanov.springbootintro.dto.drinkcategory.UpdateDrinkCategoryRequestDto;
import ivanov.springbootintro.exception.EntityNotFoundException;
import ivanov.springbootintro.mapper.DrinkCategoryMapper;
import ivanov.springbootintro.model.DrinkCategory;
import ivanov.springbootintro.repository.drinkcategory.DrinkCategoryRepository;
import ivanov.springbootintro.service.DrinkCategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DrinkCategoryServiceImpl implements DrinkCategoryService {
    private final DrinkCategoryRepository drinkCategoryRepository;
    private final DrinkCategoryMapper drinkCategoryMapper;

    @Override
    public DrinkCategoryDto create(CreateDrinkCategoryRequestDto requestDto) {
        DrinkCategory drinkCategory = drinkCategoryMapper.toEntity(requestDto);
        return drinkCategoryMapper.toDto(drinkCategoryRepository.save(drinkCategory));
    }

    @Override
    public List<DrinkCategoryDto> getAll(Pageable pageable) {
        return drinkCategoryRepository.findAll(pageable).stream()
                .map(drinkCategoryMapper::toDto)
                .toList();
    }

    @Override
    public DrinkCategoryDto getById(Long id) {
        DrinkCategory drinkCategory = assertDrinkCategoryExistsById(id);
        return drinkCategoryMapper.toDto(drinkCategory);
    }

    @Override
    public DrinkCategoryDto updateById(UpdateDrinkCategoryRequestDto requestDto, Long id) {
        assertDrinkCategoryExistsById(id);
        DrinkCategory drinkCategory = drinkCategoryMapper.toEntity(requestDto);
        drinkCategory.setId(id);
        return drinkCategoryMapper.toDto(drinkCategoryRepository.save(drinkCategory));
    }

    @Override
    public void deleteById(Long id) {
        assertDrinkCategoryExistsById(id);
        drinkCategoryRepository.deleteById(id);
    }

    private DrinkCategory assertDrinkCategoryExistsById(Long id) {
        return drinkCategoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find drink category by id=" + id));
    }
}
