package com.datamelt.artikel.model;

public enum UserRole
{
    ADMIN("admin"),
    READ_ONLY("readonly"),
    READ_WRITE("readwrite");

    private String description;

    UserRole(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
}
