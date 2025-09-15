package com.hoangkhoi.springboot_ecommerce.service.impl;

import com.hoangkhoi.springboot_ecommerce.dto.request.CartItemReqDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.CartRespDTO;
import com.hoangkhoi.springboot_ecommerce.enums.ProductStatus;
import com.hoangkhoi.springboot_ecommerce.exception.BadRequestException;
import com.hoangkhoi.springboot_ecommerce.exception.ExceptionMessages;
import com.hoangkhoi.springboot_ecommerce.exception.NotFoundException;
import com.hoangkhoi.springboot_ecommerce.mapper.CartMapper;
import com.hoangkhoi.springboot_ecommerce.model.Cart;
import com.hoangkhoi.springboot_ecommerce.model.CartItem;
import com.hoangkhoi.springboot_ecommerce.model.Product;
import com.hoangkhoi.springboot_ecommerce.model.User;
import com.hoangkhoi.springboot_ecommerce.repository.CartRepository;
import com.hoangkhoi.springboot_ecommerce.repository.ProductRepository;
import com.hoangkhoi.springboot_ecommerce.repository.UserRepository;
import com.hoangkhoi.springboot_ecommerce.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public CartRespDTO getCart(UUID userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("User ID: " + ExceptionMessages.NOT_FOUND, userId))
                );
        return cartMapper.toDto(cart);
    }

    @Override
    public CartRespDTO addItemToCart(CartItemReqDTO request) {
        Product product = getActiveProduct(request.getProductId());

        if(product.getStock() == 0) {
            throw new BadRequestException(
                    String.format(ExceptionMessages.INSUFFICIENT_STOCK, 0, "products")
            );
        }

        // if a user doesn't have a cart then create it
        Cart cart = getOrCreateCart(request.getUserId());

        // update cartItem by one if it already exists
        CartItem cartItem = findCartItem(cart, request.getProductId());

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        }
        else {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(1);
            cart.getCartItems().add(cartItem);
        }

        cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    @Override
    public void removeItemFromCart(CartItemReqDTO request) {
        Cart cart = getCartByUserId(request.getUserId());

        boolean removed = cart.getCartItems()
                .removeIf(item -> item.getProduct().getId().equals(request.getProductId()));

        if(!removed) {
            throw new NotFoundException(
                    String.format(ExceptionMessages.NOT_FOUND, "Product")
            );
        }

        cartRepository.save(cart);
    }

    @Override
    public void deleteCart(UUID cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new NotFoundException(
                        String.format(ExceptionMessages.NOT_FOUND, "Cart"))
                );

        cartRepository.delete(cart);
    }

    //----- Helper methods -----//
    private Product getActiveProduct(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(
                        String.format(ExceptionMessages.NOT_FOUND, "Product"))
                );

        if (product.getStatus() != ProductStatus.ACTIVE) {
            throw new BadRequestException(
                    String.format(
                            ExceptionMessages.PRODUCT_STATUS_IS,
                            product.getName(),
                            product.getStatus()
                    )

            );
        }

        return product;
    }

    private Cart getOrCreateCart(UUID userId) {
        return cartRepository.findByUserId(userId).orElseGet(() -> {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NotFoundException(
                            String.format(ExceptionMessages.NOT_FOUND, "User")));

            Cart newCart = new Cart();
            newCart.setUser(user);

            return cartRepository.save(newCart);
        });
    }

    private CartItem findCartItem(Cart cart, UUID productId) {
        return cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    private Cart getCartByUserId(UUID userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(
                        String.format(ExceptionMessages.NOT_FOUND, "Cart")));
    }
    //----- End helper methods -----//
}
