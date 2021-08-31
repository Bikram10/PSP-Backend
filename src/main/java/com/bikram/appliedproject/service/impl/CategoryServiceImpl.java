package com.bikram.appliedproject.service.impl;

import com.bikram.appliedproject.domain.category.Category;
import com.bikram.appliedproject.repositories.CategoryRepository;
import com.bikram.appliedproject.service.CategoryService;
import com.bikram.appliedproject.service.CloudinaryService;
import com.bikram.appliedproject.service.dto.CategoryDto;
import com.bikram.appliedproject.service.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    CloudinaryService cloudinaryService;

    public Category save(MultipartFile file, CategoryDto categoryDto) throws IOException {
        String url = cloudinaryService.upload(file, "category/"+categoryDto.getCategory_id()+"_"+categoryDto.getCategory_name().replace("\\s", "_"));

        categoryDto.setCategory_img_url(url);
        Category category = categoryMapper.DtoToCategory(categoryDto);
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAll() {

        return  categoryRepository.findAll();
    }

}
