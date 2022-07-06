package com.datamelt.artikel.model;

import com.datamelt.artikel.app.web.util.HashGenerator;

public class User
{
    private long id;
    private String name;
    private String fullName;
    private String password;
    private String type;

    public User(String name)
    {
        this.name = name;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public boolean isAdmin()
    {
        return type.equals(UserType.ADMIN.getDescription());
    }

    public boolean isReadOnlyUser()
    {
        return type.equals(UserType.READ_ONLY.getDescription());
    }

    public boolean isReadWriteUser()
    {
        return type.equals(UserType.READ_WRITE.getDescription());
    }
}
