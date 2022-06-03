package com.datamelt.artikel.adapter.web.form;

import java.util.EnumMap;

public class ProductContainerForm
{
    private EnumMap<ProductContainerFormField,String> fields = new EnumMap<ProductContainerFormField,String>(ProductContainerFormField.class);

    public void put(ProductContainerFormField key, String value)
    {
        fields.put(key, value);
    }

    public String get(ProductContainerFormField key) { return fields.get(key); }

    public long getId()
    {
        return Long.parseLong(fields.get(ProductContainerFormField.ID));
    }

    public EnumMap<ProductContainerFormField,String> getFields()
    {
        return fields;
    }
}
