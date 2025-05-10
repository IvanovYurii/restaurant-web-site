package ivanov.springbootintro.service.impl;

import ivanov.springbootintro.dto.dishcategory.CreateDishCategoryRequestDto;
import ivanov.springbootintro.dto.dishcategory.DishCategoryDto;
import ivanov.springbootintro.exception.EntityNotFoundException;
import ivanov.springbootintro.mapper.DishCategoryMapper;
import ivanov.springbootintro.model.DishCategory;
import ivanov.springbootintro.repository.dishcategory.DishCategoryRepository;
import ivanov.springbootintro.service.DishCategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DishCategoryServiceImpl implements DishCategoryService {
    private final DishCategoryRepository dishCategoryRepository;
    private final DishCategoryMapper dishCategoryMapper;

    @Override
    public DishCategoryDto create(CreateDishCategoryRequestDto requestDto) {
        DishCategory dishCategory = dishCategoryMapper.toEntity(requestDto);
        return dishCategoryMapper.toDto(dishCategoryRepository.save(dishCategory));
    }

    @Override
    public List<DishCategoryDto> getAll(Pageable pageable) {
        return dishCategoryRepository.findAll(pageable).stream()
                .map(dishCategoryMapper::toDto)
                .toList();
    }

    @Override
    public DishCategoryDto getById(Long id) {
        DishCategory dishCategory = assertDishCategoryExistsById(id);
        return dishCategoryMapper.toDto(dishCategory);
    }

    @Override
    public DishCategoryDto updateById(CreateDishCategoryRequestDto requestDto, Long id) {
        assertDishCategoryExistsById(id);
        DishCategory dishCategory = dishCategoryMapper.toEntity(requestDto);
        dishCategory.setId(id);
        return dishCategoryMapper.toDto(dishCategoryRepository.save(dishCategory));
    }

    @Override
    public void deleteById(Long id) {
        assertDishCategoryExistsById(id);
        dishCategoryRepository.deleteById(id);
    }

    private DishCategory assertDishCategoryExistsById(Long id) {
        return dishCategoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find dish category by id=" + id));
    }
}
