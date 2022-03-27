package com.datamelt.artikel.app;

import com.datamelt.artikel.adapter.csv.CsvLoader;
import com.datamelt.artikel.adapter.database.sqlite.SqliteRepository;
import com.datamelt.artikel.model.config.MainConfiguration;
import com.datamelt.artikel.service.LoaderService;
import com.datamelt.artikel.util.CsvFileType;

public class TestCsv
{
    public static void main(String[] args) throws Exception
    {
        MainConfiguration configuration = new ConfigurationLoader().getMainConfiguration();

        LoaderService service = new LoaderService(new SqliteRepository(configuration.getDatabase()));
        CsvLoader loader = new CsvLoader(service, configuration.getCsvInput());
        loader.processFile(CsvFileType.CONTAINER);
        //loader.processFile(CsvFileType.ORDER);

        System.out.println();



    }


}
