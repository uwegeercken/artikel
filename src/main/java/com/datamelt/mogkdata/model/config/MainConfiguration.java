package com.datamelt.mogkdata.model.config;

import com.datamelt.mogkdata.model.config.DatabaseConfiguration;

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
