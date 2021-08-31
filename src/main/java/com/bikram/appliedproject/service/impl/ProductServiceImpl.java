package com.bikram.appliedproject.service.impl;

import com.bikram.appliedproject.domain.product.Attributes;
import com.bikram.appliedproject.domain.product.Product;
import com.bikram.appliedproject.repositories.AttributeRepository;
import com.bikram.appliedproject.repositories.ProductRepository;
import com.bikram.appliedproject.service.CloudinaryService;
import com.bikram.appliedproject.service.ProductService;
import com.bikram.appliedproject.service.dto.AttributeDto;
import com.bikram.appliedproject.service.dto.ProductDto;
import com.bikram.appliedproject.service.mapper.AttributeMapper;
import com.bikram.appliedproject.service.mapper.ProductMapper;
import com.bikram.appliedproject.service.specification.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    AttributeMapper attributeMapper;


    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private AttributeRepository attributeRepository;

    @Override
    public ProductDto save(MultipartFile file, ProductDto productDto) throws IOException {
        Product product = productMapper.DtoToProduct(productDto);
        String url = cloudinaryService.upload(file, "category/"+productDto.getProduct_id()+"_"+productDto.getProduct_name().replace("\\s", "_"));
        product.setProduct_img_url(url);

        product = productRepository.save(product);

        Set<Attributes> attributeDtoSet = new HashSet<>();
        for(AttributeDto attributeDto: productDto.getAttributes()){
            Attributes attributes = attributeMapper.DtoToAttribute(attributeDto);
            attributes.setProduct(product);
            attributeDtoSet.add(attributes);

            attributeRepository.save(attributes);
        }
        product.setAttributes(attributeDtoSet);


        product = productRepository.save(product);
        return productMapper.productToDto(product);
    }

    @Override
    public List<Product> getAll() {
        List<Product> productList = productRepository.findAll();

        return productList;
    }

    @Override
    public Page<Product> getAllByPaging(int page, int size, String name) {
        Specification<Product> productSpecification = Specification.where(null);
        productSpecification = productSpecification.and(ProductSpecification.hasName(name));
        PageRequest pageRequest = PageRequest.of(page, size);
        return productRepository.findAll(pageRequest);
    }

    @Override
    public ProductDto getProductById(long id) {
        Product product = productRepository.getById(id);

        ProductDto productDto = productMapper.productToDto(product);

        return productDto;
    }



    @Override
    public void deleteProduct(String productId) {
        productRepository.deleteById(Long.parseLong(productId));
    }
}
