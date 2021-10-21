package com.bikram.appliedproject.service;

import com.bikram.appliedproject.domain.category.Type;
import com.bikram.appliedproject.service.dto.TypeDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CategoryService {

    TypeDto save(MultipartFile file, TypeDto typeDto) throws IOException;

    Type findByName(String name);

    List<Type> getAll();
}
