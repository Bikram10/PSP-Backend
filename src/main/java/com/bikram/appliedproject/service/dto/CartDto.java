package com.bikram.appliedproject.service.dto;


import com.bikram.appliedproject.domain.authentication.User;

import java.util.Set;

public class CartDto {

    private Long id;

    private User user;

    private Set<CartItemDto> cartItems;

    public CartDto() {
    }

    public CartDto(Long id, User user, Set<CartItemDto> cartItems) {
        this.id = id;
        this.user = user;
        this.cartItems = cartItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<CartItemDto> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItemDto> cartItems) {
        this.cartItems = cartItems;
    }
}

