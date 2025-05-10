package ivanov.springbootintro.dto.dish;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

public record CreateDishRequestDto(
        @NotEmpty String name,
        String description,
        @Min(0) Double price,
        @NotNull Long categoryId,
        Set<Long> ingredientIds,
        Integer calories,
        Integer weight,
        @NotNull Boolean available,
        String imageUrl,
        Integer preparationTime,
        Set<Long> suggestedDrinkIds
) {
}
