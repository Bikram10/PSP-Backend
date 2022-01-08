package com.bikram.appliedproject.service;

import com.bikram.appliedproject.domain.order.Order;
import com.bikram.appliedproject.service.dto.OrderDto;
import com.bikram.appliedproject.service.dto.ProductDto;

import java.util.List;

public interface OrderService {

    OrderDto placeOrder();

    Order getOrder();

    List<Order> getOrderList();

    ProductDto saveRating(ProductDto orderItemDto, Integer rate);

    void updateStatus(String orderStatus, Long orderId);
}
