package com.datamelt.artikel.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.File;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SparkJava
{
    private long staticfilesExpiretime;
    private String locale;
    private String tempFolder;
    private int tokenExpiresMinutes;
    private int port;
    private String documentsFolder;

    private String keystoreFile;
    private String keystorePassword;

    public long getStaticfilesExpiretime()
    {
        return staticfilesExpiretime;
    }

    public String getLocale() { return locale; }

    public String getTempFolder() { return tempFolder; }

    public int getTokenExpiresMinutes() { return tokenExpiresMinutes; }

    public int getPort() { return port; }

    public String getDocumentsFolder() { return documentsFolder; }

    public String getKeystoreFile()
    {
        return keystoreFile;
    }

    public String getKeystorePassword()
    {
        return keystorePassword;
    }

    public boolean existTempFolder()
    {
        File folder = new File(tempFolder);
        return folder.exists() && folder.isDirectory() && folder.canWrite();
    }
}
