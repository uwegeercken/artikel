package com.datamelt.artikel.config;

import java.io.File;

public class SparkJava
{
    private long staticfilesExpiretime;
    private String locale;
    private String tempFolder;
    private int tokenExpiresMinutes;

    public long getStaticfilesExpiretime()
    {
        return staticfilesExpiretime;
    }

    public String getLocale() { return locale; }

    public String getTempFolder() { return tempFolder; }

    public int getTokenExpiresMinutes() { return tokenExpiresMinutes; }

    public boolean existTempFolder()
    {
        File folder = new File(tempFolder);
        return folder.exists() && folder.isDirectory() && folder.canWrite();
    }
}
