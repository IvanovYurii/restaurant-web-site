package ivanov.springbootintro.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ivanov.springbootintro.dto.ingredient.CreateIngredientRequestDto;
import ivanov.springbootintro.dto.ingredient.IngredientDto;
import ivanov.springbootintro.dto.ingredient.UpdateIngredientRequestDto;
import ivanov.springbootintro.service.IngredientService;
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

@Tag(name = "Ingredient Management", description = "Endpoints for managing categories. "
        + "These endpoints provide operations related to categories management, including "
        + "retrieving a list of all available categories, finding detailed information about a "
        + "specific ingredient by its ID, creating a new ingredient, updating information about "
        + "a specific ingredient, and deleting a ingredient. "
        + "Certain operations may require administrative privileges.")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/ingredients")
public class IngredientController {
    private final IngredientService ingredientService;

    @GetMapping
    @Operation(
            summary = "Get all Ingredients",
            description = "Retrieve a list of all available ingredients. This endpoint is "
                    + "accessible to authenticated users. It returns detailed information "
                    + "about each ingredient, including ID, name."
                    + "You can use the 'page' and 'size' query parameters to paginate through the"
                    + " results."
    )
    public List<IngredientDto> getAllIngredients(
            @ParameterObject Pageable pageable
    ) {
        return ingredientService.getAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Find ingredient by id",
            description = "Retrieve detailed information about a specific ingredient by its ID. "
                    + "This endpoint is accessible to authenticated users and returns information"
                    + " such as ingredientID, name."
    )
    public IngredientDto getIngredientsById(
            @PathVariable @Min(1) Long id
    ) {
        return ingredientService.getById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @Operation(
            summary = "Create a new ingredient",
            description = "This endpoint create a new ingredient with the provided information,  "
                    + "including name. "
                    + "This operation requires the user to have the role ADMIN."
    )
    public IngredientDto createIngredients(
            @RequestBody @Valid CreateIngredientRequestDto requestDto
    ) {
        return ingredientService.create(requestDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Update a specific ingredient",
            description = "This endpoint update information about a specific ingredient by its ID. "
                    + "This operation requires the user to have the role ADMIN."
    )
    public IngredientDto updateIngredients(
            @PathVariable @Min(1) Long id,
            @RequestBody @Valid UpdateIngredientRequestDto requestDto
    ) {
        return ingredientService.updateById(requestDto, id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete ingredient by id",
            description = "Delete a specific ingredient by its ID. "
                    + "This operation requires the user to have the role ADMIN."
    )
    public void deleteIngredients(
            @PathVariable @Min(1) Long id
    ) {
        ingredientService.deleteById(id);
    }
}
