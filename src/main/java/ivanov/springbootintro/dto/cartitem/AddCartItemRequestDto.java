package ivanov.springbootintro.dto.cartitem;

import jakarta.validation.constraints.Min;

public record AddCartItemRequestDto(
        @Min(1) Long productId,
        @Min(1) int quantity) {
}
