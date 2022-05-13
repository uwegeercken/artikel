package com.datamelt.artikel.model;

public class Producer
{
    private long id;
    private String name;
    private int noOrdering = 0;

    public Producer(String name)
    {
        this.name = name;
    }

    public Producer(long id)
    {
        this.id = id;
    }

    public int getNoOrdering() { return noOrdering; }

    public void setNoOrdering(int noOrdering) { this.noOrdering = noOrdering; }

    public String getName()
    {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }
}
