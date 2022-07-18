package com.datamelt.artikel.model;

public enum UserRole
{
    ADMIN("admin"),
    READ_ONLY("readonly"),
    READ_WRITE("readwrite");

    private String role;

    UserRole(String role)
    {
        this.role = role;
    }

    public String getRole()
    {
        return role;
    }
}
