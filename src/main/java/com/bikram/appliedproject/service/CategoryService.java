package com.bikram.appliedproject.service;

import com.bikram.appliedproject.domain.category.Category;
import com.bikram.appliedproject.service.dto.CategoryDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CategoryService {

    Category save(MultipartFile file, CategoryDto categoryDto) throws IOException;

    List<Category> getAll();
}
