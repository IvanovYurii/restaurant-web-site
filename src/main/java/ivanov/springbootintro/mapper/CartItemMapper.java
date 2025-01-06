package ivanov.springbootintro.mapper;

import ivanov.springbootintro.config.MapperConfig;
import ivanov.springbootintro.dto.cartitem.AddCartItemRequestDto;
import ivanov.springbootintro.dto.cartitem.CartItemDto;
import ivanov.springbootintro.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.price", target = "price")
    CartItemDto toDto(CartItem cartItem);

    CartItem toEntity(AddCartItemRequestDto requestDto);
}
