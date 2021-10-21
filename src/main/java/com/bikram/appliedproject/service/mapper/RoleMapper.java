package com.bikram.appliedproject.service.mapper;

import com.bikram.appliedproject.domain.authentication.Role;
import com.bikram.appliedproject.service.dto.RolesDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    Role DtoToRole(RolesDto userDto);

    RolesDto roleToDto(RolesDto rolesDto);
}
