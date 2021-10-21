package com.bikram.appliedproject.service;

import com.bikram.appliedproject.domain.product.Product;
import com.bikram.appliedproject.service.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    List<ProductDto> saveCSV(MultipartFile file) throws IOException;

    List<Product> getAll();

    Page<Product> getAllByPaging(int page, int size, String name);

    List<Product> getAllProductByPaging(MultiValueMap<String, String> query);

    ProductDto getProductById(long id);

    void updateProduct(ProductDto productDto, MultipartFile file) throws IOException;

    List<Product> getFilterData(MultiValueMap<String, String> query);

    void deleteProduct(String productId);

    Page<Product> getProductByType(String typeId, int page, int size);

}
