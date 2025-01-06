package ivanov.springbootintro.dto.cartitem;

public record CartItemDto(
        Long id,
        Long productId,
        String productName,
        Double price,
        int quantity,
        Double totalPrice) {
}
