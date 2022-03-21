package com.datamelt.artikel.app;

import com.datamelt.artikel.adapter.web.WebUiApi;
import com.datamelt.artikel.adapter.database.sqlite.SqliteRepository;
import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.config.MainConfiguration;
import com.datamelt.artikel.model.Product;
import com.datamelt.artikel.service.ConfigurationLoaderService;
import com.datamelt.artikel.service.ConnectionService;
import com.datamelt.artikel.service.ControllerService;

import java.sql.Connection;

public class Test1
{
    public static void main(String[] args) throws Exception
    {
        MainConfiguration configuration = new ConfigurationLoaderService().getMainConfiguration();
        Connection connection = ConnectionService.getConnection(configuration);

        ControllerService controllerService = new ControllerService(new SqliteRepository(connection));
        WebUiApi client = new WebUiApi(controllerService);

        Producer producer = client.getProducerById(1);
        Product newProduct =  new Product.ProductBuilder("71","huhu muku", producer).build();

        client.addProduct(newProduct);
        Product product = client.getProductByNumber("1");
        System.out.println();

    }


}
