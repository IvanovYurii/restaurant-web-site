package ivanov.springbootintro.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ivanov.springbootintro.dto.drinkcategory.CreateDrinkCategoryRequestDto;
import ivanov.springbootintro.dto.drinkcategory.DrinkCategoryDto;
import ivanov.springbootintro.dto.drinkcategory.UpdateDrinkCategoryRequestDto;
import ivanov.springbootintro.service.DrinkCategoryService;
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

@Tag(name = "Drink Category Management", description = "Endpoints for managing Drink categories. "
        + "These endpoints provide operations related to categories management, including "
        + "retrieving a list of all available Drink categories, finding detailed information "
        + "about a specific category by its ID, creating a new category, updating information "
        + "about a specific category, and deleting a category. "
        + "Certain operations may require administrative privileges.")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/drink_categories")
public class DrinkCategoryController {
    private final DrinkCategoryService drinkCategoryService;

    @GetMapping
    @Operation(
            summary = "Get all Drink categories",
            description = "Retrieve a list of all available Drink categories. This endpoint is "
                    + "accessible to authenticated users. It returns detailed information "
                    + "about each category, including ID, name and description."
                    + "You can use the 'page' and 'size' query parameters to paginate through the"
                    + " results."
    )
    public List<DrinkCategoryDto> getAllDrinksCategories(
            @ParameterObject Pageable pageable
    ) {
        return drinkCategoryService.getAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Find Drink category by id",
            description = "Retrieve detailed information about a specific Drink category by its "
                    + "ID.This endpoint is accessible to authenticated users and returns "
                    + " information such as categoryID, name and description"
    )
    public DrinkCategoryDto getDrinkCategoryById(
            @PathVariable @Min(1) Long id
    ) {
        return drinkCategoryService.getById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @Operation(
            summary = "Create a new Drink category",
            description = "This endpoint create a new category with the provided information,  "
                    + "including name and description. "
                    + "This operation requires the user to have the role ADMIN."
    )
    public DrinkCategoryDto createDrinkCategory(
            @RequestBody @Valid CreateDrinkCategoryRequestDto requestDto
    ) {
        return drinkCategoryService.create(requestDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Update a specific category",
            description = "This endpoint update information about a specific category by its ID. "
                    + "This operation requires the user to have the role ADMIN."
    )
    public DrinkCategoryDto updateDrinkCategory(
            @PathVariable @Min(1) Long id,
            @RequestBody @Valid UpdateDrinkCategoryRequestDto requestDto
    ) {
        return drinkCategoryService.updateById(requestDto, id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete Drink category by id",
            description = "Delete a specific category by its ID. "
                    + "This operation requires the user to have the role ADMIN."
    )
    public void deleteDrinkCategory(
            @PathVariable @Min(1) Long id
    ) {
        drinkCategoryService.deleteById(id);
    }
}
