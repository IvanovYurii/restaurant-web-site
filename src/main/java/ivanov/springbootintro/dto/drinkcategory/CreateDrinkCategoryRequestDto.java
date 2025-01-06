package ivanov.springbootintro.dto.dishcategory;

import jakarta.validation.constraints.NotEmpty;

public record CreateDishCategoryRequestDto(
        @NotEmpty String name,
        String description) {
}
