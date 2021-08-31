package com.bikram.appliedproject.service.mapper;


import com.bikram.appliedproject.domain.product.Attributes;
import com.bikram.appliedproject.service.dto.AttributeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AttributeMapper {

    Attributes DtoToAttribute(AttributeDto attributeDto);

    AttributeDto attributeToDto(Attributes attributes);
}
