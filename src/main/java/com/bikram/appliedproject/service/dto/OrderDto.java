package com.bikram.appliedproject.service.dto;

import com.bikram.appliedproject.domain.order.OrderStatus;

import java.time.Instant;
import java.util.Set;

public class OrderDto {

    private Long id;

    private Double subTotal;

    private Double grandTotal;

    private UserDto userDto;

    private Set<OrderItemDto> orderItemDtos;

    private OrderStatus orderStatus;

    private Instant createdDate;

    private ShippingDto shippingDto;

    private String message;

    public OrderDto(){}

    public OrderDto(String msg){
        this.message = msg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public Set<OrderItemDto> getOrderItemDtos() {
        return orderItemDtos;
    }

    public void setOrderItemDtos(Set<OrderItemDto> orderItemDtos) {
        this.orderItemDtos = orderItemDtos;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public ShippingDto getShippingDto() {
        return shippingDto;
    }

    public void setShippingDto(ShippingDto shippingDto) {
        this.shippingDto = shippingDto;
    }
}
