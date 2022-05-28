package com.datamelt.artikel.util;

import com.datamelt.artikel.adapter.csv.CsvLabelFileWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtility
{
    private static final Logger logger = LoggerFactory.getLogger(FileUtility.class);

    public static String getFullFilename(String path, String filename)
    {
        if(path!=null && filename!=null)
        {
            if(path.endsWith("/"))
            {
                return path + filename;
            }
            else
            {
                return path + "/" + filename;
            }
        }
        else
        {
            return null;
        }
    }
}
