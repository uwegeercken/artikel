package com.datamelt.artikel.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MainConfiguration
{
    private DatabaseConfiguration database;
    private SparkJava sparkJava;
    private CsvInput csvInput;
    private LabelsConfiguration labels;
    private AsciidocConfiguration asciidoc;
    private EmailConfiguration email;
    private OpaConfiguration opa;
    private WebAppConfiguration webApp;

    public DatabaseConfiguration getDatabase()
    {
        return database;
    }

    public CsvInput getCsvInput() { return csvInput; }

    public SparkJava getSparkJava() { return sparkJava; }

    public LabelsConfiguration getLabels() { return labels; }

    public AsciidocConfiguration getAsciidoc() { return asciidoc; }

    public EmailConfiguration getEmail() { return email; }

    public OpaConfiguration getOpa() { return opa; }

    public WebAppConfiguration getWebApp() { return webApp; }
}
