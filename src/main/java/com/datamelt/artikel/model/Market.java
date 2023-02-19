package com.datamelt.artikel.model;

public class Market
{
    private long id;
    private String name;
    private String type;

    public Market(String name)
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

    public void setName(String name) { this.name = name; }

    public void setType(String type) { this.type = type; }

    public String getType() { return type; }
}
