package com.datamelt.artikel.adapter.web;

import java.util.HashMap;
import java.util.Map;

public class ProductForm
{
    private Map<String,String> fields = new HashMap<>();

    public void put(ProductFormField key, String value)
    {
        fields.put(key.fieldName(), value);
    }

    public String get(String key)
    {
        return fields.get(key);
    }
}
