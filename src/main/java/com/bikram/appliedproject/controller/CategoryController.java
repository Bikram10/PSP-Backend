package com.bikram.appliedproject.controller;

import com.bikram.appliedproject.domain.category.Category;
import com.bikram.appliedproject.service.CategoryService;
import com.bikram.appliedproject.service.dto.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/categoryApi")
@CrossOrigin("*")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/category")
    public ResponseEntity<Category> save(@RequestPart("file") MultipartFile file, @RequestPart("category") CategoryDto categoryDto) throws IOException {

        return ResponseEntity.ok().body(categoryService.save(file, categoryDto));
    }

    @GetMapping("/listCategory")
    public ResponseEntity<List<Category>> getAll(){
        return ResponseEntity.ok().body(categoryService.getAll());
    }


}
