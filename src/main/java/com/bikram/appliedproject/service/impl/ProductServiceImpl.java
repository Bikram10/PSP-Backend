package com.bikram.appliedproject.service.impl;

import com.bikram.appliedproject.domain.category.Type;
import com.bikram.appliedproject.domain.product.ImageUrls;
import com.bikram.appliedproject.domain.product.Product;
import com.bikram.appliedproject.repositories.CategoryRepository;
import com.bikram.appliedproject.repositories.ImageRepository;
import com.bikram.appliedproject.repositories.ProductRepository;
import com.bikram.appliedproject.service.CloudinaryService;
import com.bikram.appliedproject.service.ProductService;
import com.bikram.appliedproject.service.dto.ProductDto;
import com.bikram.appliedproject.service.helper.CSVHelper;
import com.bikram.appliedproject.service.mapper.ProductMapper;
import com.bikram.appliedproject.service.mapper.TypeMapper;
import com.bikram.appliedproject.service.specification.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductMapper productMapper;


    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    TypeMapper typeMapper;

    @Autowired
    ImageRepository imageRepository;


    @Override
    public List<ProductDto> saveCSV(MultipartFile file) throws IOException {
        List<ProductDto> productDtos = CSVHelper.csvToProduct(file.getInputStream());
        List<ProductDto> productDtoList = new ArrayList<>();



        for (ProductDto  productDto: productDtos){
            ProductDto productDto1 = new ProductDto();

            if(productDto1 !=null) {
                Type type = categoryRepository.findByName(productDto.getType().getName());
                productDto1.setBrand(productDto.getBrand());
                productDto1.setProduct_name(productDto.getProduct_name());
                productDto1.setSKU(productDto.getSKU());
                productDto1.setCategory(productDto.getCategory());
                productDto1.setType(type);
                productDto1.setShort_description(productDto.getShort_description());
                productDto1.setDescription(productDto.getDescription());
                productDto1.setPrice(productDto.getPrice());
                productDto1.setQuantity(productDto.getQuantity());
                productDto1.setStockStatus(productDto.getStockStatus());
                int i=0;
                for(ImageUrls urls: productDto.getProduct_img_url()){
                   String fileUrl = cloudinaryService.uploadImage(urls.getUrl(), "product/"+productDto.getSKU()+"/"+i++);
                  urls.setUrl(fileUrl);
                   productDto.getProduct_img_url().add(urls);
                }
                productDto1.setProduct_img_url(productDto.getProduct_img_url());
                //urlMap.put(productDto.getSKU(), productDto.getProduct_img_url());
            }

            productDtoList.add(productDto1);
        }
                List<Product> productList = productDtoList.stream().map(productDto1 ->
                new Product(productDto1.getProduct_id(), productDto1.getBrand(), productDto1.getProduct_name(), productDto1.getSKU(), productDto1.getCategory(), productDto1.getType(), productDto1.getDescription(), productDto1.getStockStatus(), productDto1.getPrice(), productDto1.getProduct_img_url(), productDto1.getQuantity(), productDto1.isClearance(), 0)).collect(Collectors.toList());

        productList = productRepository.saveAll(productList);

        List<ProductDto> productDtoList1 = productList.stream().map(product ->
                new ProductDto(product.getProduct_id(), product.getBrand(), product.getProduct_name(), product.getSKU(), product.getCategory(), product.getType(), product.getDescription(), product.getStockStatus(), product.getProduct_img_url(), product.getPrice(), product.getQuantity(), product.isClearance())).collect(Collectors.toList());

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
        productSpecification = productSpecification.and(ProductSpecification.hasProductDetail(name));
        PageRequest pageRequest = PageRequest.of(page, size);
        return productRepository.findAll(productSpecification, pageRequest);
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

//        if(file !=null){
//            urls = cloudinaryService.upload(file, file.getOriginalFilename());
//        }
//
//        if(urls.equals("")){
//            product.setProduct_img_url(product.getProduct_img_url());
//        }
//        else{
//            //product.setProduct_img_url(urls);
//        }

        productRepository.save(product);
    }

    @Override
    public List<Product> getFilterData(MultiValueMap<String, String> query) {
        Specification<Product> productSpecification = Specification.where(null);
        if(query.containsKey("name")) {
            productSpecification = productSpecification.and(ProductSpecification.hasName(query.getFirst("name")));
            //productSpecification = productSpecification.and(ProductSpecification.hasBrand(query.getFirst("name")));
        }
        if(query.containsKey("brand")){
            productSpecification = productSpecification.and(ProductSpecification.hasBrand(query.getFirst("brand")));

        }
        if(query.containsKey("min")){
            productSpecification = productSpecification.and(ProductSpecification.hasPriceMin(Integer.parseInt(query.getFirst("min"))));
        }
        if(query.containsKey("max")){
            productSpecification = productSpecification.and(ProductSpecification.hasPriceMax(Integer.parseInt(query.getFirst("max"))));
        }
        if(query.containsKey("type")){
            productSpecification = productSpecification.and(ProductSpecification.hasType(Integer.parseInt(query.getFirst("type"))));
        }

        return productRepository.findAll(productSpecification);
    }


    @Override
    public void deleteProduct(String productId) {
        productRepository.deleteById(Long.parseLong(productId));
    }

    @Override
    public Page<Product> getProductByType(String typeId, int page, int size) {
        Specification specification = Specification.where(null);
        specification = specification.and(ProductSpecification.hasType(Integer.parseInt(typeId)));
        System.out.println(specification);
        PageRequest pageRequest = PageRequest.of(page, size);
        return productRepository.findAll(specification, pageRequest);
    }
}
