package com.bikram.appliedproject.repositories;

import com.bikram.appliedproject.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
