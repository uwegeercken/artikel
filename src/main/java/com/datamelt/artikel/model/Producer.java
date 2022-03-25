package com.datamelt.artikel.model;

public class Producer
{
    private long id;
    private String name;
    private long noOrdering = 0;

    public Producer(String name)
    {
        this.name = name;
    }

    public long getNoOrdering() { return noOrdering; }

    public void setNoOrdering(long noOrdering) { this.noOrdering = noOrdering; }

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
