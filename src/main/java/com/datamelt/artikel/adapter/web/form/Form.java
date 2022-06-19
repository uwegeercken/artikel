package com.datamelt.artikel.adapter.web.form;

import spark.Request;

import java.util.EnumMap;
import java.util.Set;

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

    public static Form createFormFromQueryParameters(Request request)
    {
        Form form = new Form();
        for(String parameter : request.queryParams())
        {
            if(FormField.exists(parameter))
            {
                form.put(FormField.valueOf(parameter), request.queryParams(parameter));
            }
        }
        return form;
    }
}
