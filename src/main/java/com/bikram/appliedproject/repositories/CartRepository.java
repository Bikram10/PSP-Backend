package com.bikram.appliedproject.repositories;

import com.bikram.appliedproject.domain.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("select c from Cart c where c.user.user_id = :id")
    Optional<Cart> findByUser(Long id);

}
