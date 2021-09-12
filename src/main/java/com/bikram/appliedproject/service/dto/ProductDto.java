package com.bikram.appliedproject.service.dto;

import com.bikram.appliedproject.domain.category.Type;
import com.bikram.appliedproject.domain.product.StockStatus;


public class ProductDto {
    private long product_id;

    private String brand;

    private String product_name;

    private String SKU;

    private String category;

    private Type type;

    private StockStatus stockStatus;

    private String description;

    private double price;

    private int quantity;

    public ProductDto(){

    }

    public ProductDto(long product_id, String brand, String product_name, String SKU, String category, Type type, String description, double price, int quantity) {
        this.product_id = product_id;
        this.brand = brand;
        this.product_name = product_name;
        this.SKU = SKU;
        this.category = category;
        this.type = type;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public StockStatus getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(StockStatus stockStatus) {
        this.stockStatus = stockStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
