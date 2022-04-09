package com.datamelt.artikel.adapter.web.form;

public enum ProductFormField
{
    ID("id", "long", false,false),
    NAME("name", "string", false, false),
    DESCRIPTION("description", "string", false, true),
    NUMBER("number", "string", true, false),
    QUANTITY("quantity", "int", false, true),
    WEIGHT("weight","double", false, true),
    PRICE("price", "double", false, true),
    PRODUCER_ID("producer_id", "long", false, false),
    CONTAINER_ID("container_id", "long", false, false),
    ORIGIN_ID("origin_id", "long", false, false);

    private String fieldName;
    private String type;
    private boolean unique;
    private boolean canBeEmpty;

    ProductFormField(String fieldName, String type, boolean unique, boolean canBeEmpty)
    {
        this.fieldName = fieldName;
        this.type = type;
        this.unique = unique;
        this.canBeEmpty = canBeEmpty;
    }

    public String fieldName()
    {
        return fieldName;
    }

    public String type() { return type; }

    public boolean unique() { return unique; }

    public boolean canBeEmpty() { return canBeEmpty; }

    public static ProductFormField getUniqueField()
    {
        for(ProductFormField field : ProductFormField.values())
        {
            if(field.unique())
            {
                return field;
            }
        }
        return null;
    }
}
