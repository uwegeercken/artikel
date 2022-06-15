package com.datamelt.artikel.config;

public class AsciidocConfiguration
{
    private String templateFileFolder;
    private String pdfOutputFolder;
    private String themeFile;

    public String getTemplateFileFolder() { return templateFileFolder; }

    public String getThemeFile()
    {
        return themeFile;
    }

    public String getPdfOutputFolder() { return pdfOutputFolder; }
}
