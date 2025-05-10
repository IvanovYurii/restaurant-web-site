package ivanov.springbootintro.dto.ingredient;

import jakarta.validation.constraints.NotEmpty;

public record CreateIngredientRequestDto(
        @NotEmpty String name) {
}
