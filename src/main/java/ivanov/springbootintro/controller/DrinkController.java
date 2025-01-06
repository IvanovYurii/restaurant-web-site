package ivanov.springbootintro.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ivanov.springbootintro.dto.drink.CreateDrinkRequestDto;
import ivanov.springbootintro.dto.drink.DrinkDto;
import ivanov.springbootintro.dto.drink.DrinkDtoWithCategoryNameLikesCount;
import ivanov.springbootintro.dto.drink.DrinkDtoWithCategoryNameListUserLikes;
import ivanov.springbootintro.dto.drink.UpdateDrinkRequestDto;
import ivanov.springbootintro.service.DrinkService;
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

@Tag(name = "Drink Management", description = "Endpoints for managing drinks. "
        + "These endpoints provide operations related to drinks management, including "
        + "retrieving a list of all available drinks, finding detailed information about a "
        + "specific drink by its ID, creating a new drink, updating information about "
        + "a specific drink, and deleting a drink. "
        + "Certain operations may require administrative privileges.")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/drinks")
public class DrinkController {
    private final DrinkService drinkService;

    @GetMapping
    @Operation(
            summary = "Get all drinks",
            description = "Retrieve a list of all available drinks. This endpoint is "
                    + "accessible to authenticated users. It returns detailed information "
                    + "about each drinks."
                    + "You can use the 'page' and 'size' query parameters to paginate through the"
                    + " results."
    )
    public List<DrinkDtoWithCategoryNameLikesCount> getAllDrinks(
            @ParameterObject Pageable pageable
    ) {
        return drinkService.getAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Find drinks by id",
            description = "Retrieve detailed information about a specific drink by its ID. "
                    + "This endpoint is accessible to authenticated users and returns information"
                    + " about drink."
    )
    public DrinkDtoWithCategoryNameListUserLikes getDrinkById(
            @PathVariable @Min(1) Long id
    ) {
        return drinkService.getById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @Operation(
            summary = "Create a new Drink.",
            description = "This endpoint create a new drink with the provided information.  "
                    + "This operation requires the user to have the role ADMIN."
    )
    public DrinkDto createDrink(
            @RequestBody @Valid CreateDrinkRequestDto requestDto
    ) {
        return drinkService.create(requestDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Update a specific drink.",
            description = "This endpoint update information about a specific drink by its ID. "
                    + "This operation requires the user to have the role ADMIN."
    )
    public DrinkDto updateDrink(
            @PathVariable @Min(1) Long id,
            @RequestBody @Valid UpdateDrinkRequestDto requestDto
    ) {
        return drinkService.updateById(requestDto, id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete drink by id",
            description = "Delete a specific drink by its ID. "
                    + "This operation requires the user to have the role ADMIN."
    )
    public void deleteDrink(
            @PathVariable @Min(1) Long id
    ) {
        drinkService.deleteById(id);
    }

    @GetMapping("/{id}/drinks")
    @Operation(summary = "List drinks by drink category",
            description = "Retrieve detailed information about drinks associated with a specific "
                    + "drink category by its ID. This endpoint is accessible to authenticated"
                    + " users and returns information drink."
                    + "You can use the 'page' and 'size' query parameters to paginate through the"
                    + " results."
    )
    public List<DrinkDtoWithCategoryNameLikesCount> getDrinksByDrinkCategoryId(
            @PathVariable @Min(1) Long id,
            @ParameterObject Pageable pageable
    ) {
        return drinkService.getDrinksByDrinkCategoryId(id, pageable);

    }
}
