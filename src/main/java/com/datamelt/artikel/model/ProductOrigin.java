package com.datamelt.artikel.model;

public class ProductOrigin
{
    private long id;
    private String name;

    public ProductOrigin(String name)
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

}
