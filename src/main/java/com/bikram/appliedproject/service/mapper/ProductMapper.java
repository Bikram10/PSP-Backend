package com.bikram.appliedproject.service.mapper;

import com.bikram.appliedproject.domain.product.Product;
import com.bikram.appliedproject.service.dto.ProductDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {


    Product DtoToProduct(ProductDto productDto);

    ProductDto productToDto(Product product);
}
