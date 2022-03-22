package com.datamelt.artikel.adapter.database.sqlite;

import com.datamelt.artikel.model.Market;
import com.datamelt.artikel.model.Order;
import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.Product;

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

    public static List<Order> getAllOrders(Connection connection) throws Exception
    {
        List<Order> orders = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet resultset = stmt.executeQuery("select * from productorder");
        while(resultset.next())
        {
            Order order = new Order(resultset.getString("number"),resultset.getLong("timestamp"));
            order.setId(resultset.getLong("id"));
            order.setItems(getAllOrderItems(connection, order));
            orders.add(order);
        }
        resultset.close();
        stmt.close();
        return orders;
    }

    public static List<Product> getAllOrderItems(Connection connection, Order order) throws Exception
    {
        List<Product> products = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet resultset = stmt.executeQuery("select product_id from productorder_item where productorder_id=" + order.getId());
        while(resultset.next())
        {
            Product product = ProductSearch.getProductById(connection, resultset.getLong("product_id"));
            products.add(product);
        }
        resultset.close();
        stmt.close();
        return products;
    }
}
