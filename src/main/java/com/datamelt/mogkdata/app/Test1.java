package com.datamelt.mogkdata.app;

import com.datamelt.mogkdata.adapter.web.WebUiApi;
import com.datamelt.mogkdata.adapter.database.sqlite.SqliteRepository;
import com.datamelt.mogkdata.model.Producer;
import com.datamelt.mogkdata.model.config.MainConfiguration;
import com.datamelt.mogkdata.model.Product;
import com.datamelt.mogkdata.service.ConfigurationLoaderService;
import com.datamelt.mogkdata.service.ConnectionService;
import com.datamelt.mogkdata.service.ControllerService;

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
