package ivanov.springbootintro.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ivanov.springbootintro.dto.dishcategory.CreateDishCategoryRequestDto;
import ivanov.springbootintro.dto.dishcategory.DishCategoryDto;
import ivanov.springbootintro.service.DishCategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Dish Category Management", description = "Endpoints for managing Dish categories. "
        + "These endpoints provide operations related to Dish categories management, including "
        + "retrieving a list of all available categories, finding detailed information about a "
        + "specific category by its ID, creating a new category, updating information about "
        + "a specific category, and deleting a category. "
        + "Certain operations may require administrative privileges.")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/dish_categories")
public class DishCategoryController {
    private final DishCategoryService dishCategoryService;

    @GetMapping
    @Operation(
            summary = "Get all Dish categories",
            description = "Retrieve a list of all available Dish categories. This endpoint is "
                    + "accessible to authenticated users. It returns detailed information "
                    + "about each category, including ID, name and description."
                    + "You can use the 'page' and 'size' query parameters to paginate through the"
                    + " results."
    )
    public List<DishCategoryDto> getAllDishCategories(
            @ParameterObject Pageable pageable
    ) {
        return dishCategoryService.getAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Find Dish category by id",
            description = "Retrieve detailed information about a specific Dish category by its ID."
                    + " This endpoint is accessible to authenticated users and returns information"
                    + " such as categoryID, name and description"
    )
    public DishCategoryDto getDishCategoryById(
            @PathVariable @Min(1) Long id
    ) {
        return dishCategoryService.getById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @Operation(
            summary = "Create a new Dish category",
            description = "This endpoint create a new Dish category with the provided information,"
                    + " including name and description. "
                    + "This operation requires the user to have the role ADMIN."
    )
    public DishCategoryDto createDishCategory(
            @RequestBody @Valid CreateDishCategoryRequestDto requestDto
    ) {
        return dishCategoryService.create(requestDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Update a specific category",
            description = "This endpoint update information about a specific category by its ID. "
                    + "This operation requires the user to have the role ADMIN."
    )
    public DishCategoryDto updateDishCategory(
            @PathVariable @Min(1) Long id,
            @RequestBody @Valid CreateDishCategoryRequestDto requestDto
    ) {
        return dishCategoryService.updateById(requestDto, id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete category by id",
            description = "Delete a specific category by its ID. "
                    + "This operation requires the user to have the role ADMIN."
    )
    public void deleteDishCategory(
            @PathVariable @Min(1) Long id
    ) {
        dishCategoryService.deleteById(id);
    }
}
