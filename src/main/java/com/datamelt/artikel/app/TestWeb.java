package com.datamelt.artikel.app;

import com.datamelt.artikel.adapter.web.WebUiApi;
import com.datamelt.artikel.adapter.database.sqlite.SqliteRepository;
import com.datamelt.artikel.model.Market;
import com.datamelt.artikel.model.Order;
import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.config.MainConfiguration;
import com.datamelt.artikel.model.Product;
import com.datamelt.artikel.service.ControllerService;

import java.util.List;

public class TestWeb
{
    public static void main(String[] args) throws Exception
    {
        MainConfiguration configuration = new ConfigurationLoader().getMainConfiguration();

        ControllerService controllerService = new ControllerService(new SqliteRepository(configuration.getDatabase()));
        WebUiApi client = new WebUiApi(controllerService);

        List<Product> products = client.getAllProducts();
        List<Producer> producers = client.getAllProducers();
        List<Market> markets = client.getAllMarkets();
        List<Order> orders = client.getAllOrders();

        client.

        System.out.println();

    }


}
