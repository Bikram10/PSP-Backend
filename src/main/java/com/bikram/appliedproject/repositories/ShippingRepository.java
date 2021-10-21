package com.bikram.appliedproject.repositories;

import com.bikram.appliedproject.domain.order.Shipping;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ShippingRepository extends CrudRepository<Shipping, Long> {
    @Query("select s from Shipping s where s.user.user_id = :id")
    Optional<Shipping> findByUser(Long id);
}
