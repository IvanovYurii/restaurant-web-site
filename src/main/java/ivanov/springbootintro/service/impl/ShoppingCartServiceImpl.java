package ivanov.springbootintro.service.impl;

import ivanov.springbootintro.dto.cartitem.AddCartItemRequestDto;
import ivanov.springbootintro.dto.cartitem.CartItemDto;
import ivanov.springbootintro.dto.cartitem.UpdateCartItemQuantityProductRequestDto;
import ivanov.springbootintro.dto.shoppingcart.ShoppingCartDto;
import ivanov.springbootintro.exception.EntityNotFoundException;
import ivanov.springbootintro.mapper.CartItemMapper;
import ivanov.springbootintro.mapper.ShoppingCartMapper;
import ivanov.springbootintro.model.CartItem;
import ivanov.springbootintro.model.Product;
import ivanov.springbootintro.model.ShoppingCart;
import ivanov.springbootintro.model.User;
import ivanov.springbootintro.repository.cartitem.CartItemRepository;
import ivanov.springbootintro.repository.product.ProductRepository;
import ivanov.springbootintro.repository.shoppingcart.ShoppingCartRepository;
import ivanov.springbootintro.service.ShoppingCartService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Override
    public ShoppingCartDto getUserShoppingCart(User user, Pageable pageable) {
        ShoppingCart shoppingCart = shoppingCartRepository
                .getShoppingCartByUserEmail(user.getEmail());
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Transactional
    @Override
    public CartItemDto addItemToShoppingCart(User user, AddCartItemRequestDto requestDto) {
        ShoppingCart shoppingCart = shoppingCartRepository
                .getShoppingCartByUserEmail(user.getEmail());
        Optional<CartItem> cartItemWithProductPresent = cartItemRepository
                .getCartItemByproductIdAndShoppingCartId(
                        requestDto.productId(), shoppingCart.getId());
        CartItem cartItem;
        if (cartItemWithProductPresent.isPresent()) {
            cartItem = cartItemWithProductPresent.get();
            double oldTotalPrice = cartItem.getTotalPrice();
            cartItem.setQuantity(cartItem.getQuantity()
                    + requestDto.quantity());
            cartItem.setTotalPrice(cartItem.getProductPrice()
                    * cartItem.getQuantity());
            shoppingCart.setTotalCost(shoppingCart.getTotalCost()
                    - oldTotalPrice
                    + cartItem.getTotalPrice());
        } else {
            cartItem = new CartItem();
            Product product = productRepository.findById(requestDto.productId())
                    .orElseThrow(() -> new EntityNotFoundException("Can't find product by id "
                            + requestDto.productId()));
            cartItem.setProduct(product);
            cartItem.setProductPrice(product.getPrice());
            cartItem.setShoppingCart(shoppingCart);
            cartItem.setQuantity(requestDto.quantity());
            cartItem.setTotalPrice(product.getPrice()
                    * requestDto.quantity());
            shoppingCart.setTotalCost(shoppingCart.getTotalCost()
                    + cartItem.getTotalPrice());
            shoppingCart.getCartItems().add(cartItem);
        }
        cartItemRepository.save(cartItem);
        shoppingCartRepository.save(shoppingCart);
        return cartItemMapper.toDto(cartItem);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public CartItemDto updateCartItemQuantity(
            User user,
            Long cartItemId,
            UpdateCartItemQuantityProductRequestDto updateDto) {
        CartItem cartItem = findCartItemInShoppingCart(user.getId(), cartItemId);
        cartItem.setQuantity(updateDto.quantity());
        cartItem.setTotalPrice(cartItem.getProductPrice()
                * cartItem.getQuantity());
        double oldTotalPrice = cartItem.getTotalPrice();
        cartItemRepository.save(cartItem);
        ShoppingCart shoppingCart = shoppingCartRepository
                .getShoppingCartByUserEmail(user.getEmail());
        double difference = cartItem.getTotalPrice()
                - oldTotalPrice;
        shoppingCart.setTotalCost(shoppingCart.getTotalCost()
                + difference);
        shoppingCartRepository.save(shoppingCart);
        return cartItemMapper.toDto(cartItem);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteProductFromShoppingCart(User user, Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("CartItem not found"));
        double totalPriceToRemove = cartItem.getTotalPrice();
        cartItemRepository.delete(cartItem);
        ShoppingCart shoppingCart = shoppingCartRepository
                .getShoppingCartByUserEmail(user.getEmail());
        shoppingCart.setTotalCost(shoppingCart.getTotalCost()
                - totalPriceToRemove);
        shoppingCartRepository.save(shoppingCart);
    }

    private CartItem findCartItemInShoppingCart(
            Long userId, Long cartItemId) {
        return cartItemRepository
                .getCartItemByIdAndShoppingCartId(cartItemId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find item by id "
                        + cartItemId));
    }
}
