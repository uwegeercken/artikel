package com.datamelt.artikel.model;

public class Producer
{
    private long id;
    private String name;

    public Producer(String name)
    {
        this.name = name;
    }
    public String getName()
    {
        return name;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }
}
