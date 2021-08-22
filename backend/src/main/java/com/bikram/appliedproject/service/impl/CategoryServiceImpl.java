package com.bikram.appliedproject.service.impl;

import com.bikram.appliedproject.domain.category.Category;
import com.bikram.appliedproject.repositories.CategoryRepository;
import com.bikram.appliedproject.service.CategoryService;
import com.bikram.appliedproject.service.dto.CategoryDto;
import com.bikram.appliedproject.service.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryMapper categoryMapper;

    public Category save(CategoryDto categoryDto){
        Category category = categoryMapper.DtoToCategory(categoryDto);
        return categoryRepository.save(category);
    }

}
