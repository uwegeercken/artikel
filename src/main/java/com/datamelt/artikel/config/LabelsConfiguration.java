package com.datamelt.artikel.config;

import java.io.File;

public class LabelsConfiguration
{
    private String glabelsBinary;
    private String glabelsFile;
    private String tempFolder;

    public String getGlabelsFile()
    {
        return glabelsFile;
    }

    public String getTempFolder()
    {
        return tempFolder;
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

    public boolean existTempFolder()
    {
        File folder = new File(tempFolder);
        return folder.exists() && folder.isDirectory() && folder.canWrite();
    }

    public boolean existBinary()
    {
        File binary = new File(glabelsBinary);
        return binary.exists() && binary.canExecute();
    }


}
