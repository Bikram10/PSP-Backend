package com.bikram.appliedproject.service.dto;

public class CartItemDto {


    private Long id;

    private ProductDto product;

    private Integer quantity;

    private Double subTotal;

    public CartItemDto(Long id, ProductDto product, Integer quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    public CartItemDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


    public Double getSubtotal(){
        subTotal = getProduct().getPrice() * getQuantity();
        return subTotal;
    }
}
