package com.datamelt.artikel.adapter.web.form;

public enum ProductFormField
{
    ID("id", "long"),
    NAME("name", "string"),
    DESCRIPTION("description", "string"),
    NUMBER("number", "string"),
    QUANTITY("quantity", "int"),
    WEIGHT("weight","double"),
    PRICE("price", "double"),
    PRODUCER_ID("producer_id", "long"),
    CONTAINER_ID("container_id", "long"),
    ORIGIN_ID("origin_id", "long");

    private String fieldName;
    private String type;

    ProductFormField(String fieldName, String type)
    {
        this.fieldName = fieldName;
        this.type = type;
    }

    public String fieldName()
    {
        return fieldName;
    }

    public String type() { return type; }
}
