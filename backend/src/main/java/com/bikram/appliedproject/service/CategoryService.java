package com.bikram.appliedproject.service;

import com.bikram.appliedproject.domain.category.Category;
import com.bikram.appliedproject.service.dto.CategoryDto;

public interface CategoryService {

    Category save(CategoryDto categoryDto);
}
