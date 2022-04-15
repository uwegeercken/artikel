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

    public MainConfiguration getMainConfiguration(String filename)
    {
        try
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
        catch (Exception e)
        {
            logger.debug("exception loading configuration from file: [{}] ", e.getMessage());
            return null;
        }
    }
    public MainConfiguration getMainConfiguration()
    {
        try
        {
            return loadConfiguration();
        }
        catch (Exception e)
        {
            logger.debug("exception loading configuration from classpath: [{}] ", e.getMessage());
            return null;
        }
    }

    private MainConfiguration loadConfiguration() throws Exception {
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL resource = classLoader.getResource("config.yaml");
        ObjectMapper config = new YAMLMapper();
        return config.readValue(new File(resource.getFile()), MainConfiguration.class);
    }

    private MainConfiguration loadConfiguration(File file) throws Exception {
        ObjectMapper config = new YAMLMapper();
        return config.readValue(file, MainConfiguration.class);
    }
}
