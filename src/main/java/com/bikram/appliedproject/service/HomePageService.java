package com.bikram.appliedproject.service;

import com.bikram.appliedproject.domain.product.Product;
import com.bikram.appliedproject.service.dto.ProductDto;
import org.springframework.data.domain.Page;

public interface HomePageService {

    Page<Product> getNewItem(int page, int size);

    ProductDto getProductInfo(long id);

    Page<Product> getViewedItem(int page, int size);

    Page<Product> getRecommendedItems(int page, int size, String type);
}
