package com.bikram.appliedproject.repositories;

import com.bikram.appliedproject.domain.category.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Type, Long> {

    Type findByName(String name);
}
