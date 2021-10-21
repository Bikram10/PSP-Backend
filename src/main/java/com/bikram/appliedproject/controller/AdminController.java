package com.bikram.appliedproject.controller;

import com.bikram.appliedproject.domain.product.Product;
import com.bikram.appliedproject.repositories.ProductRepository;
import com.bikram.appliedproject.service.ProductService;
import com.bikram.appliedproject.service.dto.ProductDto;
import com.bikram.appliedproject.service.helper.CSVHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/productApi")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository pagingRepository ;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/upload")
    public ResponseEntity<List<ProductDto>> uploadFile(@RequestPart("file") MultipartFile file) throws IOException {
        if (CSVHelper.hasCSVFormat(file)) {
            return ResponseEntity.ok().body(productService.saveCSV(file));
        }
        return ResponseEntity.ok().body(null);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listProduct")
    public ResponseEntity<List<Product>> getAll(){
        return ResponseEntity.ok().body(productService.getAll());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/paging")
    public ResponseEntity<Page<Product>> getAllByPaging(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam("name") String name) {
       return ResponseEntity.ok().body(productService.getAllByPaging(page, size, name));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductDto> getProducts(@PathVariable long productId){
        ProductDto productDto = productService.getProductById(productId);

        return ResponseEntity.ok().body(productDto);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete/{productId}")
    public void deleteProduct(@PathVariable String productId)
    {
        productService.deleteProduct(productId);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/product-detail")
    public ResponseEntity<List<Product>> getDetails(@RequestParam MultiValueMap<String, String> query){
        return ResponseEntity.ok().body(productService.getAllProductByPaging(query));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/product/update")
    public void updateProduct(@RequestPart("product") ProductDto productDto, @RequestPart("file") MultipartFile file) throws IOException {
        productService.updateProduct(productDto, file);
    }
}
