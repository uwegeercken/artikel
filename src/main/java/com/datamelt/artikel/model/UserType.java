package com.datamelt.artikel.model;

public enum UserType
{
    ADMIN("admin"),
    READ_ONLY("readonly"),
    READ_WRITE("readwrite");

    private String description;

    UserType(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
}
