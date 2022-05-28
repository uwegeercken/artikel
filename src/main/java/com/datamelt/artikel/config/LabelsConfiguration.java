package com.datamelt.artikel.config;

import java.io.File;

public class LabelsConfiguration
{
    private String glabelsBinary;
    private String glabelsFile;

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


}
