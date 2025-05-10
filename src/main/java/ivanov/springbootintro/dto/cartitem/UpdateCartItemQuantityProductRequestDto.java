package ivanov.springbootintro.dto.cartitem;

import jakarta.validation.constraints.Min;

public record UpdateCartItemQuantityProductRequestDto(
        @Min(1) int quantity) {
}
