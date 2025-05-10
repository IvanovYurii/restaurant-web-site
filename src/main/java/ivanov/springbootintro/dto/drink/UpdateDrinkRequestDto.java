package ivanov.springbootintro.dto.drink;

public record UpdateDrinkRequestDto(
        String name,
        Double price,
        String description,
        Integer volume,
        Long categoryId,
        Boolean available,
        Double alcoholPercentage,
        Integer calories,
        String imageUrl
) {
}
