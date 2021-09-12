package com.bikram.appliedproject.service.dto;

public class TypeDto {

    private long type_id;

    private String name;

    private String url;

    public long getType_id() {
        return type_id;
    }

    public void setCategory_id(long type_id) {
        this.type_id = type_id;
    }

    public String getName() {
        return name;
    }

    public void setCategory_name(String name) {
        this.name = name;
    }

    public String getCategory_img_url() {
        return url;
    }

    public void setCategory_img_url(String url) {
        this.url = url;
    }


}
