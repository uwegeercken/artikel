package com.datamelt.artikel.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

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

    public static boolean checkReadAccessFolder(String path)
    {
        boolean canRead=false;
        File folder = new File(path);
        if(folder.exists() && folder.isDirectory())
        {
            if(folder.canRead())
            {
                canRead = true;
            }
            else
            {
                logger.error("no read access to folder: [{}]", path);
            }
        }
        else
        {
            logger.error("the specified location does not exist or is not a folder: [{}]", path);
        }
        return canRead;
    }

    public static boolean checkReadWriteAccessFolder(String path)
    {
        boolean canReadWrite=false;
        File folder = new File(path);
        if(folder.exists() && folder.isDirectory())
        {
            if(folder.canRead() && folder.canWrite())
            {
                canReadWrite = true;
            }
            else
            {
                logger.error("no read/write access to folder: [{}]", path);
            }
        }
        else
        {
            logger.error("the specified location does not exist or is not a folder: [{}]", path);
        }
        return canReadWrite;
    }

    public static boolean checkReadAccessFile(String path)
    {
        boolean canRead=false;
        File file = new File(path);
        if(file.isFile())
        {
            if(file.canRead())
            {
                canRead = true;
            }
            else
            {
                logger.error("no read access to file: [{}]", path);
            }
        }
        else
        {
            logger.error("the specified object is not a file: [{}]", path);
        }
        return canRead;
    }

    public static boolean checkExecuteAccessFile(String path)
    {
        boolean canExecute=false;
        File file = new File(path);
        if(file.isFile())
        {
            if(file.canExecute())
            {
                canExecute = true;
            }
            else
            {
                logger.error("no execution right on file: [{}]", path);
            }
        }
        else
        {
            logger.error("the specified object is not a file: [{}]", path);
        }
        return canExecute;
    }
}
