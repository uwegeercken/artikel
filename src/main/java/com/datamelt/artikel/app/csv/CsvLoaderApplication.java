package com.datamelt.artikel.app.csv;

import com.datamelt.artikel.adapter.csv.CsvLoader;
import com.datamelt.artikel.adapter.database.sqlite.SqliteRepository;
import com.datamelt.artikel.app.ConfigurationLoader;
import com.datamelt.artikel.config.MainConfiguration;
import com.datamelt.artikel.service.LoaderService;
import com.datamelt.artikel.util.CsvFileType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CsvLoaderApplication
{
    private static final Logger logger =  LoggerFactory.getLogger(CsvLoaderApplication.class);

    public static void main(String[] args) throws Exception
    {
        MainConfiguration configuration;
        if(args!=null && args.length>0)
        {
            logger.info("loading configuration from file: [{}] ", args[0]);
            configuration = new ConfigurationLoader().getMainConfiguration(args[0]);
        }
        else
        {
            throw new Exception("a configuration yaml file is required");

        }

        logger.info("start loading CSV data");

        LoaderService service = new LoaderService(new SqliteRepository(configuration.getDatabase()));
        CsvLoader loader = new CsvLoader(service, configuration.getCsvInput());
        loader.processFile(CsvFileType.CONTAINER);
        loader.processFile(CsvFileType.PRODUCER);
        loader.processFile(CsvFileType.PRODUCER);
        loader.processFile(CsvFileType.MARKET);
        loader.processFile(CsvFileType.ORIGIN);
        loader.processFile(CsvFileType.PRODUCT);
        loader.processFile(CsvFileType.ORDER);
        loader.processFile(CsvFileType.ORDERITEMS);
        loader.processFile(CsvFileType.USER);

        logger.info("loading of CSV data complete ");
    }
}
