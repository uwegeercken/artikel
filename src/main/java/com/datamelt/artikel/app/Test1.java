package com.datamelt.artikel.app;

import com.datamelt.artikel.adapter.web.WebUiApi;
import com.datamelt.artikel.adapter.database.sqlite.SqliteRepository;
import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.config.MainConfiguration;
import com.datamelt.artikel.model.Product;
import com.datamelt.artikel.service.ControllerService;

public class Test1
{
    public static void main(String[] args) throws Exception
    {
        MainConfiguration configuration = new ConfigurationLoader().getMainConfiguration();

        ControllerService controllerService = new ControllerService(new SqliteRepository(configuration.getDatabase()));
        WebUiApi client = new WebUiApi(controllerService);

        Producer producer = client.getProducerById(1);
        Product newProduct =  new Product.ProductBuilder("71","huhu muku", producer).build();

        client.addProduct(newProduct);
        //Product product = client.getProductByNumber("1");
        System.out.println();

    }


}
