package com.bikram.appliedproject.repositories;

import com.bikram.appliedproject.domain.order.OrderItem;
import org.springframework.data.repository.CrudRepository;

public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {
}
