package com.bikram.appliedproject.controller;

import com.bikram.appliedproject.domain.product.Product;
import com.bikram.appliedproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productUserApi")
@CrossOrigin("*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/filterData")
    public ResponseEntity<List<Product>> getFilterData(@RequestParam MultiValueMap<String, String> query){
        return ResponseEntity.ok().body(productService.getFilterData(query));
    }

    @GetMapping("/productsByType")
    public ResponseEntity<Page<Product>> getProductsByType(@RequestParam String typeId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok().body(productService.getProductByType(typeId, page, size));
    }
}
