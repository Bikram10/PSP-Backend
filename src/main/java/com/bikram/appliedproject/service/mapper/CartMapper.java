package com.bikram.appliedproject.service.mapper;

import com.bikram.appliedproject.domain.cart.Cart;
import com.bikram.appliedproject.domain.cart.CartItem;
import com.bikram.appliedproject.service.dto.CartDto;
import com.bikram.appliedproject.service.dto.CartItemDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {

    Cart DtoToCart(CartDto cartDto);

    CartDto cartToDto(Cart cart);

    CartItem DtoToCartItem(CartItemDto cartItemDto);

    CartItemDto cartItemToDto(CartItem cartItem);
}
