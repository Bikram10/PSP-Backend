package com.bikram.appliedproject.service.mapper;


import com.bikram.appliedproject.domain.category.Category;
import com.bikram.appliedproject.service.dto.CategoryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category DtoToCategory(CategoryDto categoryDto);

    CategoryDto categoryToDto(Category category);
}
