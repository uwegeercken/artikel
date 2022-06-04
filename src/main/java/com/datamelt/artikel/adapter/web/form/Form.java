package com.datamelt.artikel.adapter.web.form;

import java.util.EnumMap;

public class Form
{
    private EnumMap<FormField,String> fields = new EnumMap<FormField,String>(FormField.class);

    public void put(FormField key, String value)
    {
        fields.put(key, value);
    }

    public String get(FormField key) { return fields.get(key); }

    public long getId()
    {
        return Long.parseLong(fields.get(FormField.ID));
    }

    public EnumMap<FormField,String> getFields()
    {
        return fields;
    }
}
