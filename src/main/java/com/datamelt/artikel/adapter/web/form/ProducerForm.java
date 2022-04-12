package com.datamelt.artikel.adapter.web.form;

import java.util.EnumMap;

public class ProducerForm
{
    private EnumMap<ProducerFormField,String> fields = new EnumMap<ProducerFormField,String>(ProducerFormField.class);

    public void put(ProducerFormField key, String value)
    {
        fields.put(key, value);
    }

    public String get(ProducerFormField key) { return fields.get(key); }

    public long getId()
    {
        return Long.parseLong(fields.get(ProducerFormField.ID));
    }

    public EnumMap<ProducerFormField,String> getFields()
    {
        return fields;
    }
}
