package com.datamelt.mogkdata.app;

import com.datamelt.mogkdata.adapter.csv.CsvLoader;
import com.datamelt.mogkdata.adapter.database.sqlite.SqliteRepository;
import com.datamelt.mogkdata.model.config.MainConfiguration;
import com.datamelt.mogkdata.service.ConfigurationLoaderService;
import com.datamelt.mogkdata.service.ConnectionService;
import com.datamelt.mogkdata.service.LoaderService;
import com.datamelt.mogkdata.util.CsvFileType;

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
