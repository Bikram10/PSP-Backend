package com.bikram.appliedproject.repositories;

import com.bikram.appliedproject.domain.cart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
