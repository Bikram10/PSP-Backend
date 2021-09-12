package com.bikram.appliedproject.service.impl;

import com.bikram.appliedproject.domain.category.Type;
import com.bikram.appliedproject.domain.product.Product;
import com.bikram.appliedproject.repositories.CategoryRepository;
import com.bikram.appliedproject.repositories.ProductRepository;
import com.bikram.appliedproject.service.CloudinaryService;
import com.bikram.appliedproject.service.ProductService;
import com.bikram.appliedproject.service.dto.ProductDto;
import com.bikram.appliedproject.service.helper.CSVHelper;
import com.bikram.appliedproject.service.mapper.AttributeMapper;
import com.bikram.appliedproject.service.mapper.ProductMapper;
import com.bikram.appliedproject.service.specification.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private CategoryRepository categoryRepository;

    @Override
    public ProductDto save(MultipartFile file, ProductDto productDto) throws IOException {
        Product product = productMapper.DtoToProduct(productDto);
        String url = cloudinaryService.upload(file, "category/"+productDto.getProduct_id()+"_"+productDto.getProduct_name().replace("\\s", "_"));
        product.setProduct_img_url(url);

        product = productRepository.save(product);

        return productMapper.productToDto(product);
    }

    @Override
    public List<ProductDto> saveCSV(MultipartFile file) throws IOException {
        List<ProductDto> productDtos = CSVHelper.csvToProduct(file.getInputStream());
        List<ProductDto> productDtoList = new ArrayList<>();

        for (ProductDto  productDto: productDtos){
            ProductDto productDto1 = new ProductDto();
            Type type = categoryRepository.findByName(productDto.getType().getName());
            productDto1.setBrand(productDto.getBrand());
            productDto1.setProduct_name(productDto.getProduct_name());
            productDto1.setSKU(productDto.getSKU());
            productDto1.setCategory(productDto.getCategory());
            productDto1.setType(type);
            productDto1.setDescription(productDto.getDescription());
            productDto1.setPrice(productDto.getPrice());
            productDto1.setQuantity(productDto.getQuantity());

            productDtoList.add(productDto1);
        }
                List<Product> productList = productDtoList.stream().map(productDto1 ->
                new Product(productDto1.getProduct_id(), productDto1.getBrand(), productDto1.getProduct_name(), productDto1.getSKU(), productDto1.getCategory(), productDto1.getType(), productDto1.getDescription(), productDto1.getPrice(), productDto1.getQuantity())).collect(Collectors.toList());

        productList = productRepository.saveAll(productList);

        List<ProductDto> productDtoList1 = productList.stream().map(product ->
                new ProductDto(product.getProduct_id(), product.getBrand(), product.getProduct_name(), product.getSKU(), product.getCategory(), product.getType(), product.getDescription(), product.getPrice(), product.getQuantity())).collect(Collectors.toList());

        return productDtoList1;
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
    public List<Product> getAllProductByPaging(MultiValueMap<String, String> query) {
        Specification<Product> productSpecification = Specification.where(null);
        if(query.containsKey("name")){
            productSpecification = productSpecification.and(ProductSpecification.hasName(query.getFirst("name")));
        }
        if(query.containsKey("category")){
            productSpecification = productSpecification.and(ProductSpecification.hasCategory(query.getFirst("category")));
        }
       return productRepository.findAll(productSpecification);
    }

    @Override
    public ProductDto getProductById(long id) {
        Product product = productRepository.getById(id);

        ProductDto productDto = productMapper.productToDto(product);

        return productDto;
    }

    @Override
    public void updateProduct(ProductDto productDto, MultipartFile file) throws IOException {
        Product product = productMapper.DtoToProduct(productDto);
        product = productRepository.getById(product.getProduct_id());

        String urls = "";

        if(file !=null){
            urls = cloudinaryService.upload(file, file.getOriginalFilename());
        }

        if(urls.equals("")){
            product.setProduct_img_url(product.getProduct_img_url());
        }
        else{
            product.setProduct_img_url(urls);
        }

        productRepository.save(product);
    }


    @Override
    public void deleteProduct(String productId) {
        productRepository.deleteById(Long.parseLong(productId));
    }
}
