package ivanov.springbootintro.dto.dishcategory;

import jakarta.validation.constraints.NotEmpty;

public record UpdateDishCategoryRequestDto(
        @NotEmpty String name,
        String description) {
}
