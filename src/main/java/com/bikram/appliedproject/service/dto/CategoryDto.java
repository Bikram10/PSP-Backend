package com.bikram.appliedproject.service.dto;

public class CategoryDto {

    private long category_id;

    private String category_name;

    private String category_img_url;

    public long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(long category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_img_url() {
        return category_img_url;
    }

    public void setCategory_img_url(String category_img_url) {
        this.category_img_url = category_img_url;
    }
}
