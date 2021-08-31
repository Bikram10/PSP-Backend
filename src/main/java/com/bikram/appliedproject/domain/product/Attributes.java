package com.bikram.appliedproject.domain.product;

import javax.persistence.*;

@Entity
public class Attributes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String attributeType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private String attributeName;

    public Attributes() {
    }

    public Attributes(long id, String attributeType, Product product) {
        this.id = id;
        this.attributeType = attributeType;
        this.product = product;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }
}
