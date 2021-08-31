package com.bikram.appliedproject.service;

import com.bikram.appliedproject.domain.product.Product;
import com.bikram.appliedproject.service.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    ProductDto save(MultipartFile file, ProductDto productDto) throws IOException;

    List<Product> getAll();

    Page<Product> getAllByPaging(int page, int size, String name);

    ProductDto getProductById(long id);

    void deleteProduct(String productId);
}
