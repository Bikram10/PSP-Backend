package com.bikram.appliedproject.service.mapper;

import com.bikram.appliedproject.domain.order.Order;
import com.bikram.appliedproject.domain.order.OrderItem;
import com.bikram.appliedproject.service.dto.OrderDto;
import com.bikram.appliedproject.service.dto.OrderItemDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order DtoToOrder(OrderDto orderDto);

    OrderDto orderToDto(Order order);

    OrderItem DtoToOrderItem(OrderItemDto orderItemDto);

    OrderItemDto orderItemToDto(OrderItem orderItem);
}
