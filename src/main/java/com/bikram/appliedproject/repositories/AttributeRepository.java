package com.bikram.appliedproject.repositories;

import com.bikram.appliedproject.domain.product.Attributes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttributeRepository extends JpaRepository<Attributes, Long> {
}
