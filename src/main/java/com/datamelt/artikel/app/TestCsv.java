package com.datamelt.artikel.app;

import com.datamelt.artikel.adapter.csv.CsvLoader;
import com.datamelt.artikel.adapter.database.sqlite.SqliteRepository;
import com.datamelt.artikel.model.config.MainConfiguration;
import com.datamelt.artikel.service.ConfigurationLoaderService;
import com.datamelt.artikel.service.ConnectionService;
import com.datamelt.artikel.service.LoaderService;
import com.datamelt.artikel.util.CsvFileType;

import java.sql.Connection;

public class TestCsv
{
    public static void main(String[] args) throws Exception
    {
        MainConfiguration configuration = new ConfigurationLoaderService().getMainConfiguration();
        Connection connection = ConnectionService.getConnection(configuration);

        LoaderService service = new LoaderService(new SqliteRepository(connection));
        CsvLoader loader = new CsvLoader(service, configuration.getCsvInput());
        loader.processFile(CsvFileType.PRODUCT);

        System.out.println();

    }


}
