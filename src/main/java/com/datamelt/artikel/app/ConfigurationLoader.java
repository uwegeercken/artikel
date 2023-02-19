package com.datamelt.artikel.app;

import com.datamelt.artikel.app.web.WebApplication;
import com.datamelt.artikel.config.MainConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;

public class ConfigurationLoader
{
    private static final Logger logger =  LoggerFactory.getLogger(ConfigurationLoader.class);

    public MainConfiguration getMainConfiguration(String filename) throws Exception
    {
        File configFile = new File(filename);
        if(configFile.exists() && configFile.canRead())
        {
            return loadConfiguration(configFile);
        }
        else
        {
            logger.debug("could not load configuration from file: [{}]", filename);
            return null;
        }
    }

    private MainConfiguration loadConfiguration(File file) throws Exception {
        ObjectMapper config = new YAMLMapper();
        return config.readValue(file, MainConfiguration.class);
    }
}
