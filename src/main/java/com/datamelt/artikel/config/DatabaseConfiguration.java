package com.datamelt.artikel.config;

public class DatabaseConfiguration
{
    private String connection;
    private String jdbcClass;
    private String name;

    public String getConnection()
    {
        return connection;
    }

    public String getJdbcClass()
    {
        return jdbcClass;
    }

    public String getName() { return name; }
}
