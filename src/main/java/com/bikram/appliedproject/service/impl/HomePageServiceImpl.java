package com.bikram.appliedproject.service.impl;

import com.bikram.appliedproject.domain.product.Product;
import com.bikram.appliedproject.repositories.HomeRepository;
import com.bikram.appliedproject.repositories.ProductRepository;
import com.bikram.appliedproject.service.HomePageService;
import com.bikram.appliedproject.service.dto.ProductDto;
import com.bikram.appliedproject.service.mapper.ProductMapper;
import com.bikram.appliedproject.service.specification.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HomePageServiceImpl implements HomePageService {

    @Autowired
    private HomeRepository homeRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<Product> getNewItem(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return homeRepository.findAllOrderByDateDsc(pageRequest);
    }

    @Override
    public Page<Product> getViewedItem(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        System.out.println(homeRepository.findAllProductByView(pageRequest));
        return homeRepository.findAllProductByView(pageRequest);
    }

    @Override
    public Page<Product> getRecommendedItems(int page, int size, String type) {
        Specification productSpecification = Specification.where(null);
        productSpecification = productSpecification.and(ProductSpecification.hasType(Integer.parseInt(type)));
        PageRequest pageRequest = PageRequest.of(page, size);

        return productRepository.findAll(productSpecification, pageRequest);
    }

    @Override
    public ProductDto getProductInfo(long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        Product product = optionalProduct.isPresent() ? optionalProduct.get() : null;
        product.setView(product.getView()+1);
        productRepository.save(product);

        return productMapper.productToDto(product);
    }





}
