package ivanov.springbootintro.dto.drink;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import jdk.jfr.BooleanFlag;

public record CreateDrinkRequestDto(
        @NotEmpty(message = "The Drink name is required")
        String name,
        @NotNull(message = "The price is required")
        @Min(0) BigDecimal price,
        String description,
        Integer volume,
        @NotNull(message = "The drink category ID is required")
        Long categoryId,
        @BooleanFlag Boolean available,
        Double alcoholPercentage,
        Integer calories,
        String imageUrl
) {
}
