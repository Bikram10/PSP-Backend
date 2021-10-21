package com.bikram.appliedproject.service;

import com.bikram.appliedproject.service.dto.CartDto;
import com.bikram.appliedproject.service.dto.CartItemDto;

import java.util.List;

public interface CartService {

    CartDto saveCart(CartItemDto cartItemDto) throws Exception;

    CartDto getCart();

    Integer getCartTotal();

    void deleteCart(Long cartId) throws Exception;

    void deleteCartItems(Long UserId);

    List<CartDto> getCartItems();

    CartDto updateCartItemQuantity(CartItemDto cartDto);

}
