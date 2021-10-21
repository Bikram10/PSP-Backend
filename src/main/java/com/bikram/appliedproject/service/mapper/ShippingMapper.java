package com.bikram.appliedproject.service.mapper;

import com.bikram.appliedproject.domain.order.Shipping;
import com.bikram.appliedproject.service.dto.ShippingDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShippingMapper {

    Shipping DtoToShipping(ShippingDto shippingDto);

    ShippingDto shippingToDto(Shipping shipping);
}
