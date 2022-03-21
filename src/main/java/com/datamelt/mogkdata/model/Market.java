package com.datamelt.mogkdata.model;

public class Market
{
    private long id;
    private String name;

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

}
