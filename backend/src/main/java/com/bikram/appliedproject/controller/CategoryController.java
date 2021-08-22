package com.bikram.appliedproject.controller;

import com.bikram.appliedproject.domain.category.Category;
import com.bikram.appliedproject.repositories.CategoryRepository;
import com.bikram.appliedproject.service.CategoryService;
import com.bikram.appliedproject.service.dto.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categoryApi")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/category")
    public ResponseEntity<Category> save(CategoryDto categoryDto){
        return ResponseEntity.ok().body(categoryService.save(categoryDto));
    }


}
