package com.bikram.appliedproject.service.impl;

import com.bikram.appliedproject.domain.category.Type;
import com.bikram.appliedproject.repositories.CategoryRepository;
import com.bikram.appliedproject.service.CategoryService;
import com.bikram.appliedproject.service.CloudinaryService;
import com.bikram.appliedproject.service.dto.TypeDto;
import com.bikram.appliedproject.service.mapper.TypeMapper;
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
    TypeMapper categoryMapper;

    @Autowired
    CloudinaryService cloudinaryService;

    public TypeDto save(MultipartFile file, TypeDto typeDto) throws IOException {
        Type type = categoryMapper.DtoToCategory(typeDto);

        String url = cloudinaryService.upload(file, "category/"+ type.getType_id()+"_"+ typeDto.getName().replace("\\s", "_"));

        type.setType_img_url(url);

        type = categoryRepository.save(type);
        return categoryMapper.categoryToDto(type);
    }

    @Override
    public Type findByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Type> getAll() {

        return  categoryRepository.findAll();
    }

}
