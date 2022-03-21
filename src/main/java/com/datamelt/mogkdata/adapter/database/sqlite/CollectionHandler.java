package com.datamelt.mogkdata.adapter.database.sqlite;

import com.datamelt.mogkdata.model.Market;
import com.datamelt.mogkdata.model.Producer;
import com.datamelt.mogkdata.model.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

class CollectionHandler
{
    public static List<Product> getAllProducts(Connection connection) throws Exception
    {
        List<Product> products = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet resultset = stmt.executeQuery("select * from product");
        while(resultset.next())
        {
            Producer producer = ProducerSearch.getProducerById(connection, resultset.getLong("producer_id"));

            Product product = new Product.ProductBuilder(resultset.getString("number"), resultset.getString("name"), producer)
                    .id(resultset.getLong("id"))
                    .description(resultset.getString("description"))
                    .build();
            products.add(product);
        }
        resultset.close();
        stmt.close();
        return products;
    }

    public static List<Producer> getAllProducers(Connection connection) throws Exception
    {
        List<Producer> producers = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet resultset = stmt.executeQuery("select * from producer");
        while(resultset.next())
        {
            Producer producer = new Producer(resultset.getString("name"));
            producer.setId(resultset.getLong("id"));
            producers.add(producer);
        }
        resultset.close();
        stmt.close();
        return producers;
    }

    public static List<Market> getAllMarkets(Connection connection) throws Exception
    {
        List<Market> markets = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet resultset = stmt.executeQuery("select * from producer");
        while(resultset.next())
        {
            Market market = new Market(resultset.getString("name"));
            market.setId(resultset.getLong("id"));
            markets.add(market);
        }
        resultset.close();
        stmt.close();
        return markets;
    }


}
