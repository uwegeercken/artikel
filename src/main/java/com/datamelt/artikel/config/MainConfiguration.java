package com.datamelt.artikel.config;

public class MainConfiguration
{
    private DatabaseConfiguration database;
    private SparkJava sparkJava;
    private CsvInput csvInput;

    public DatabaseConfiguration getDatabase()
    {
        return database;
    }

    public CsvInput getCsvInput()
    {
        return csvInput;
    }

    public SparkJava getSparkJava() { return sparkJava; }
}
