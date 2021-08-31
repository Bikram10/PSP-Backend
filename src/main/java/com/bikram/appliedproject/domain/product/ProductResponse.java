package com.bikram.appliedproject.domain.product;

public class ProductResponse {

    private long id;

    private String product_name;

    private double product_price;

    public ProductResponse(Product product){
        this.id = product.getProduct_id();
        this.product_name = product.getProduct_name();
        this.product_price = product.getProduct_price();

    }

    public long getId(){
        return id;
    }

    public String getProduct_name(){
        return product_name;
    }

    public double getProduct_price(){
        return product_price;
    }
}
