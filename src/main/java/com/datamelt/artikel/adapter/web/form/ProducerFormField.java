package com.datamelt.artikel.adapter.web.form;

public enum ProducerFormField
{
    ID("id", "long", false,false),
    NAME("name", "string", true, false),
    NO_ORDERING("noordering", "int", false, false);

    private String fieldName;
    private String type;
    private boolean unique;
    private boolean canBeEmpty;

    ProducerFormField(String fieldName, String type, boolean unique, boolean canBeEmpty)
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

    public static ProducerFormField getUniqueField()
    {
        for(ProducerFormField field : ProducerFormField.values())
        {
            if(field.unique())
            {
                return field;
            }
        }
        return null;
    }
}
