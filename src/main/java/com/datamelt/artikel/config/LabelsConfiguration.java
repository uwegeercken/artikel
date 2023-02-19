package com.datamelt.artikel.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.File;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LabelsConfiguration
{
    private String glabelsBinary;
    private String glabelsFile;
    private String pdfOutputFolder;

    public String getGlabelsFile()
    {
        return glabelsFile;
    }

    public String getGlabelsBinary()
    {
        return glabelsBinary;
    }

    public boolean existGlabelsFile()
    {
        File labelsFile = new File(glabelsFile);
        return labelsFile.exists() && labelsFile.canRead();
    }

    public boolean existBinary()
    {
        File binary = new File(glabelsBinary);
        return binary.exists() && binary.canExecute();
    }

    public String getPdfOutputFolder()
    {
        return pdfOutputFolder;
    }
}
