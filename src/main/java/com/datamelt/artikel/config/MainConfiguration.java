package com.datamelt.artikel.config;

public class MainConfiguration
{
    private DatabaseConfiguration database;
    private SparkJava sparkJava;
    private CsvInput csvInput;
    private LabelsConfiguration labels;
    private AsciidocConfiguration asciidoc;
    private EmailConfiguration email;

    public DatabaseConfiguration getDatabase()
    {
        return database;
    }

    public CsvInput getCsvInput()
    {
        return csvInput;
    }

    public SparkJava getSparkJava() { return sparkJava; }

    public LabelsConfiguration getLabels() { return labels; }

    public AsciidocConfiguration getAsciidoc() { return asciidoc; }

    public EmailConfiguration getEmail() { return email; }
}
