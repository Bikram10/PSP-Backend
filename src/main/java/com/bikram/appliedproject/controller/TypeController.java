package com.bikram.appliedproject.controller;

import com.bikram.appliedproject.domain.category.Type;
import com.bikram.appliedproject.service.CategoryService;
import com.bikram.appliedproject.service.dto.TypeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/typeApi")
@CrossOrigin("*")
public class TypeController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/type")
    public ResponseEntity<TypeDto> save(@RequestPart("file") MultipartFile file, @RequestPart("category") TypeDto typeDto) throws IOException {

        return ResponseEntity.ok().body(categoryService.save(file, typeDto));
    }

    @GetMapping("/listType")
    public ResponseEntity<List<Type>> getAll(){
        return ResponseEntity.ok().body(categoryService.getAll());
    }


}
