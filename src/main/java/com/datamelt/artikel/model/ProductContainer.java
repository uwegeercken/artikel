package com.datamelt.artikel.model;

public class ProductContainer
{
    private long id;
    private String name;

    public ProductContainer(String name)
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
