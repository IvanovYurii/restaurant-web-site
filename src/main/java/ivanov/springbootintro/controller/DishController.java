package ivanov.springbootintro.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ivanov.springbootintro.dto.dish.CreateDishRequestDto;
import ivanov.springbootintro.dto.dish.DishDtoWithDishNamesLikesCount;
import ivanov.springbootintro.dto.dish.DishDtoWithDishNamesUsersLikes;
import ivanov.springbootintro.service.DishService;
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

@Tag(name = "Dish Management", description = "Endpoints for managing Dishes. "
        + "These endpoints provide operations related to dishes management, including "
        + "retrieving a list of all available dishes, finding detailed information about a "
        + "specific dish by its ID, creating a new dish, updating information about "
        + "a specific dish, and deleting a dish. "
        + "Certain operations may require administrative privileges.")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/dishes")
public class DishController {
    private final DishService dishService;

    @GetMapping
    @Operation(
            summary = "Get all dishes",
            description = "Retrieve a list of all available dishes. This endpoint is "
                    + "accessible to authenticated users. It returns detailed information "
                    + "about each dish, including ID, name and description."
                    + "You can use the 'page' and 'size' query parameters to paginate through the"
                    + " results."
    )
    public List<DishDtoWithDishNamesLikesCount> getAllDishes(
            @ParameterObject Pageable pageable
    ) {
        return dishService.getAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Find dish by id",
            description = "Retrieve detailed information about a specific dish by its ID. "
                    + "This endpoint is accessible to authenticated users and returns information"
                    + " such as dishID, name and description"
    )
    public DishDtoWithDishNamesUsersLikes getDishById(
            @PathVariable @Min(1) Long id
    ) {
        return dishService.getById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @Operation(
            summary = "Create a new dish",
            description = "This endpoint create a new dish with the provided information,  "
                    + "including name and description. "
                    + "This operation requires the user to have the role ADMIN."
    )
    public DishDtoWithDishNamesLikesCount createDish(
            @RequestBody @Valid CreateDishRequestDto requestDto
    ) {
        return dishService.create(requestDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Update a specific dish",
            description = "This endpoint update information about a specific dish by its ID. "
                    + "This operation requires the user to have the role ADMIN."
    )
    public DishDtoWithDishNamesUsersLikes updateDish(
            @PathVariable @Min(1) Long id,
            @RequestBody @Valid CreateDishRequestDto requestDto
    ) {
        return dishService.updateById(requestDto, id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete dish by id",
            description = "Delete a specific dish by its ID. "
                    + "This operation requires the user to have the role ADMIN."
    )
    public void deleteDish(
            @PathVariable @Min(1) Long id
    ) {
        dishService.deleteById(id);
    }

    @GetMapping("/{id}/dishes")
    @Operation(summary = "List dishes by dish dish",
            description = "Retrieve detailed information about dishes associated with a specific "
                    + "dish by its ID. This endpoint is accessible to authenticated users and "
                    + "returns information about dishes."
                    + "You can use the 'page' and 'size' query parameters to paginate through the"
                    + " results."
    )
    public List<DishDtoWithDishNamesLikesCount> getDishesByDishDishId(
            @PathVariable @Min(1) Long id,
            @ParameterObject Pageable pageable
    ) {
        return dishService.getDishesByDishCategoryId(id, pageable);

    }
}
