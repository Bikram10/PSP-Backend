package com.bikram.appliedproject.controller;

import com.bikram.appliedproject.domain.product.Product;
import com.bikram.appliedproject.repositories.ProductRepository;
import com.bikram.appliedproject.service.ProductService;
import com.bikram.appliedproject.service.dto.ProductDto;
import com.bikram.appliedproject.service.helper.CSVHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/productApi")
@CrossOrigin("*")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository pagingRepository ;

    @PostMapping("/product")
    public ResponseEntity<ProductDto> save(@RequestPart("file") MultipartFile file, @RequestPart("product") ProductDto productDto) throws IOException {

        return ResponseEntity.ok().body(productService.save(file, productDto));
    }

    @PostMapping("/upload")
    public ResponseEntity<List<ProductDto>> uploadFile(@RequestPart("file") MultipartFile file) throws IOException {
        if (CSVHelper.hasCSVFormat(file)) {
            System.out.println(file.getOriginalFilename());
            return ResponseEntity.ok().body(productService.saveCSV(file));
        }
        return ResponseEntity.ok().body(null);
    }
    @GetMapping("/listProduct")
    public ResponseEntity<List<Product>> getAll(){
        return ResponseEntity.ok().body(productService.getAll());
    }

    @GetMapping("/paging")
    public ResponseEntity<Page<Product>> getAllByPaging(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam("name") String name) {
       return ResponseEntity.ok().body(productService.getAllByPaging(page, size, name));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductDto> getProducts(@PathVariable long productId){
        ProductDto productDto = productService.getProductById(productId);

        return ResponseEntity.ok().body(productDto);
    }


    @PostMapping("/delete/{productId}")
    public void deleteProduct(@PathVariable String productId)
    {
        productService.deleteProduct(productId);
    }


    @GetMapping("/product-detail")
    public ResponseEntity<List<Product>> getDetails(@RequestParam MultiValueMap<String, String> query){
        return ResponseEntity.ok().body(productService.getAllProductByPaging(query));
    }

    @PostMapping("/product/update")
    public void updateProduct(@RequestPart("product") ProductDto productDto, @RequestPart("file") MultipartFile file) throws IOException {
        productService.updateProduct(productDto, file);
    }
}
