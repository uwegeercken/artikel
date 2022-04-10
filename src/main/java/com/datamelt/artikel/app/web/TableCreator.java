package com.datamelt.artikel.app.web;

import com.datamelt.artikel.adapter.database.sqlite.SqliteRepository;
import com.datamelt.artikel.app.ConfigurationLoader;
import com.datamelt.artikel.config.MainConfiguration;
import com.datamelt.artikel.service.WebService;

public class TableCreator
{
    public static void main(String[] args) throws Exception
    {
        MainConfiguration configuration = new ConfigurationLoader().getMainConfiguration();
        WebService service = new WebService(new SqliteRepository(configuration.getDatabase()));

        service.createDatabaseTables();

    }
}
