package com.datamelt.artikel.adapter.web.form;

import java.util.EnumMap;

public class ProductForm
{
    private EnumMap<ProductFormField,String> fields = new EnumMap<ProductFormField,String>(ProductFormField.class);

    public void put(ProductFormField key, String value)
    {
        fields.put(key, value);
    }

    public String get(ProductFormField key)
    {

        return fields.get(key);
    }

    public long getId()
    {
        return Long.parseLong(fields.get(ProductFormField.ID));
    }

    public EnumMap<ProductFormField,String> getFields()
    {
        return fields;
    }
}
