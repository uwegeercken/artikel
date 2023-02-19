package com.datamelt.artikel.adapter.web.form;

public enum FormField
{
    ID("id", "long", false,false),
    NAME("name", "string", true, false),
    NO_ORDERING("no_ordering", "int", false, false),
    MARKET_TYPE("market_type", "long", false, false),
    PRODUCT_NAME("product_name", "string", false, false),
    TITLE("title", "string", false, true),
    SUBTITLE("subtitle", "string", false, true),
    NUMBER("number", "string", true, false),
    QUANTITY("quantity", "int", false, true),
    WEIGHT("weight","double", false, true),
    PRICE("price", "double", false, true),
    UNAVAILABLE("unavailable", "int", false, true),
    PRODUCER_ID("producer_id", "long", false, false),
    EMAIL("email", "string", false, true),
    CONTAINER_ID("container_id", "long", false, false),
    ORIGIN_ID("origin_id", "long", false, false),
    MARKET_ID("market_id", "long", false, false),
    PASSWORD("password", "string", false, false),
    PASSWORD_NEW("password_new", "string", false, false),
    PASSWORD_NEW_REPEATED("password_new_repeated", "string", false, false),
    USER_ROLE("user_role", "string", false, false),
    FULL_NAME("full_name", "string", false, false);


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
