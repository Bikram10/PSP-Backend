package com.bikram.appliedproject.domain.product;

import com.bikram.appliedproject.domain.AbstractAuditingEntity;
import com.bikram.appliedproject.domain.cart.CartItem;
import com.bikram.appliedproject.domain.category.Type;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Product extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long product_id;

    private String brand;

    private String product_name;

    private String SKU;

    private String category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_type")
    private Type type;

    @Enumerated(EnumType.STRING)
    private StockStatus stockStatus;

    private String short_description;

    private String description;

    private double price;

    private int quantity;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_urls")
    private Set<ImageUrls> product_img_url;

    private boolean clearance;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private CartItem cartItem;

    private int view;

    private int rate;



    public Product() {
    }

    public Product(long product_id, String brand, String product_name, String SKU, String category, Type type, String short_description, String description, StockStatus stockStatus, double price, Set<ImageUrls> product_img_url, int quantity, boolean clearance, int view) {
        this.product_id = product_id;
        this.brand = brand;
        this.product_name = product_name;
        this.SKU = SKU;
        this.category = category;
        this.stockStatus = stockStatus;
        this.type = type;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.clearance = clearance;
        this.view = view;
        this.product_img_url = product_img_url;
        this.short_description = short_description;
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

    public Set<ImageUrls> getProduct_img_url() {
        return product_img_url;
    }

    public void setProduct_img_url(Set<ImageUrls> product_img_url) {
        this.product_img_url = product_img_url;
    }

    public boolean isClearance(){
        return clearance;
    }

    public void setClearance(boolean clearance){
        this.clearance = clearance;
    }

    public CartItem getCartItem() {
        return cartItem;
    }

    public void setCartItem(CartItem cartItem) {
        this.cartItem = cartItem;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
