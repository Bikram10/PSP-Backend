package com.bikram.appliedproject.repositories;

import com.bikram.appliedproject.domain.order.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Long> {
    @Query("select o from Order o where o.user.user_id = :id")
    Optional<Order> findByUser(Long id);
}
