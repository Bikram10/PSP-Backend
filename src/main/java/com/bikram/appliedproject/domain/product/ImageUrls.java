package com.bikram.appliedproject.domain.product;

import javax.persistence.*;

@Entity
public class ImageUrls {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String url;


    public ImageUrls(){}

    public ImageUrls(String url){
        this.url = url;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
