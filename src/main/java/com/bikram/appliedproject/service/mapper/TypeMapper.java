package com.bikram.appliedproject.service.mapper;


import com.bikram.appliedproject.domain.category.Type;
import com.bikram.appliedproject.service.dto.TypeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TypeMapper {
    Type DtoToCategory(TypeDto typeDto);

    TypeDto categoryToDto(Type type);
}
