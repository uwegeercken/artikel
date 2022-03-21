package com.datamelt.artikel.model.config;

public class MainConfiguration
{
    private DatabaseConfiguration database;
    private CsvInput csvInput;

    public DatabaseConfiguration getDatabase()
    {
        return database;
    }

    public CsvInput getCsvInput()
    {
        return csvInput;
    }
}
