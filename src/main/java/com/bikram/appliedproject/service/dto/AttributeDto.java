package com.bikram.appliedproject.service.dto;

public class AttributeDto {

    private long id;

    private String attributeType;

    private String attributeName;


    public long getId() {
        return id;
    }

    public AttributeDto(long id, String attributeType, String attributeName) {
        this.id = id;
        this.attributeType = attributeType;
        this.attributeName = attributeName;
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


    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }
}
