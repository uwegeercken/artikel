package com.datamelt.artikel.adapter.web.form;

public enum FormField
{
    ID("id", "long", false,false),
    NAME("name", "string", true, false),
    NO_ORDERING("no_ordering", "int", false, false);

    private String fieldName;
    private String type;
    private boolean unique;
    private boolean canBeEmpty;

    FormField(String fieldName, String type, boolean unique, boolean canBeEmpty)
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

    public static boolean exists(String fieldname) {
        boolean exists = false;
        for (FormField field : values()) {
            if (field.fieldName().equalsIgnoreCase(fieldname)) {
                exists = true;
                break;
            }
        }
        return exists;
    }
}
