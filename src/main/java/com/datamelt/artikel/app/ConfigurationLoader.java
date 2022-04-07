package com.datamelt.artikel.app;

import com.datamelt.artikel.config.MainConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.File;
import java.net.URL;

public class ConfigurationLoader
{
    public MainConfiguration getMainConfiguration()
    {
        try
        {
            return loadConfiguration();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private MainConfiguration loadConfiguration() throws Exception
    {
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL resource = classLoader.getResource("config.yaml");
        ObjectMapper config = new YAMLMapper();
        return config.readValue(new File(resource.getFile()), MainConfiguration.class);
    }
}
