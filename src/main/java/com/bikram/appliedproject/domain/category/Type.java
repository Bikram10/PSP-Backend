package com.bikram.appliedproject.domain.category;

import com.bikram.appliedproject.domain.product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;


@Entity
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long type_id;

    private String name;

    private String type_img_url;

    @OneToMany(mappedBy= "type")
    @JsonIgnore
    private Set<Product> items;


    public Type() {
    }

    public Type(String name) {
        this.name = name;
    }

    public long getType_id() {
        return type_id;
    }

    public void setType_id(long type_id) {
        this.type_id = type_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String type_name) {
        this.name = type_name;
    }

    public void setType_img_url(String type_img_url) {
        this.type_img_url = type_img_url;
    }

    public String getType_img_url() {
        return type_img_url;
    }

    public Set<Product> getItems() {
        return items;
    }

    public void setItems(Set<Product> items) {
        this.items = items;
    }
}
