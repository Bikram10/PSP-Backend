package com.bikram.appliedproject.service.dto;

import java.util.Set;

public class ProductDto {
        private long product_id;

        private String product_name;

        private CategoryDto category;

        private String product_description;

        private double product_price;

        private int product_quantity;

        private String stockStatus;

        private String product_img_url;

        private Set<AttributeDto> attributes;


    public ProductDto(long product_id, String product_name, CategoryDto category, String product_description, double product_price, int product_quantity, String stockStatus, String product_img_url, Set<AttributeDto> attributes) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.category = category;
        this.product_description = product_description;
        this.product_price = product_price;
        this.product_quantity = product_quantity;
        this.stockStatus = stockStatus;
        this.product_img_url = product_img_url;
        this.attributes = attributes;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public double getProduct_price() {
        return product_price;
    }

    public void setProduct_price(double product_price) {
        this.product_price = product_price;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }

    public String getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(String stockStatus) {
        this.stockStatus = stockStatus;
    }

    public String getProduct_img_url() {
        return product_img_url;
    }

    public Set<AttributeDto> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<AttributeDto> attributes) {
        this.attributes = attributes;
    }

    public void setProduct_img_url(String product_img_url) {
        this.product_img_url = product_img_url;
    }
}
