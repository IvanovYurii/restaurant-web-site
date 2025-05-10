package ivanov.springbootintro.dto.drinkcategory;

import jakarta.validation.constraints.NotEmpty;

public record CreateDrinkCategoryRequestDto(
        @NotEmpty String name,
        String description) {
}
