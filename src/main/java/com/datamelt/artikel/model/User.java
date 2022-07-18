package com.datamelt.artikel.model;

public class User
{
    private long id;
    private String name;
    private String fullName;
    private String password;
    private String role;

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

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }

    public boolean isAdmin() { return role.equals(UserRole.ADMIN.getRole()); }

    public boolean isReadOnlyUser()
    {
        return role.equals(UserRole.READ_ONLY.getRole());
    }

    public boolean isReadWriteUser()
    {
        return role.equals(UserRole.READ_WRITE.getRole());
    }
}
