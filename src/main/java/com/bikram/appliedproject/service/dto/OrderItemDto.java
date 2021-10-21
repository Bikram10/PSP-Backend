package com.bikram.appliedproject.service.dto;

public class OrderItemDto {

    private Long id;

    private Integer quantity;

    private ProductDto productDto;

    private Double total;


    public OrderItemDto(Double total, Long id, ProductDto productDto, Integer quantity) {
        this.id = id;
        this.productDto = productDto;
        this.quantity = quantity;
        this.total = total;
    }

    public ProductDto getProductDto() {
        return productDto;
    }

    public void setProductDto(ProductDto productDto) {
        this.productDto = productDto;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
