package com.datamelt.artikel.adapter.web;

public enum ProductFormField
{
    NAME("name"),
    DESCRIPTION("description"),
    NUMBER("number"),
    QUANTITY("quantity"),
    WEIGHT("weight"),
    PRICE("price"),
    PRODUCER_ID("producer_id"),
    ORIGIN_ID("origin_id");

    private String fieldName;

    ProductFormField(String fieldName)
    {
        this.fieldName = fieldName;
    }

    public String fieldName()
    {
        return fieldName;
    }
}
