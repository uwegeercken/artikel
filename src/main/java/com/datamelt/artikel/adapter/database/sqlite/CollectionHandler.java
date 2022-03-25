package com.datamelt.artikel.adapter.database.sqlite;

import com.datamelt.artikel.model.Market;
import com.datamelt.artikel.model.Order;
import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

class CollectionHandler
{
    public static final String SQL_QUERY_PRODUCTS = "select * from product";
    public static final String SQL_QUERY_PRODUCERS = "select * from producer";
    public static final String SQL_QUERY_MARKETS = "select * from market";
    public static final String SQL_QUERY_ORDERS = "select * from productorder";
    public static final String SQL_QUERY_ORDER_ITEMS = "select * from productorder_item where productorder_id=?";

    public static List<Product> getAllProducts(Connection connection) throws Exception
    {
        List<Product> products = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_PRODUCTS);
        ResultSet resultset = statement.executeQuery();
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
        statement.close();
        return products;
    }

    public static List<Producer> getAllProducers(Connection connection) throws Exception
    {
        List<Producer> producers = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_PRODUCERS);
        ResultSet resultset = statement.executeQuery();
        while(resultset.next())
        {
            Producer producer = new Producer(resultset.getString("name"));
            producer.setId(resultset.getLong("id"));
            producers.add(producer);
        }
        resultset.close();
        statement.close();
        return producers;
    }

    public static List<Market> getAllMarkets(Connection connection) throws Exception
    {
        List<Market> markets = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_MARKETS);
        ResultSet resultset = statement.executeQuery();
        while(resultset.next())
        {
            Market market = new Market(resultset.getString("name"));
            market.setId(resultset.getLong("id"));
            markets.add(market);
        }
        resultset.close();
        statement.close();
        return markets;
    }

    public static List<Order> getAllOrders(Connection connection) throws Exception
    {
        List<Order> orders = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_ORDERS);
        ResultSet resultset = statement.executeQuery();
        while(resultset.next())
        {
            Order order = new Order(resultset.getString("number"),resultset.getLong("timestamp"));
            order.setId(resultset.getLong("id"));
            order.setItems(getAllOrderItems(connection, order));
            orders.add(order);
        }
        resultset.close();
        statement.close();
        return orders;
    }

    public static List<Product> getAllOrderItems(Connection connection, Order order) throws Exception
    {
        List<Product> products = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_ORDER_ITEMS);
        statement.setLong(1,order.getId());
        ResultSet resultset = statement.executeQuery();
        while(resultset.next())
        {
            Product product = ProductSearch.getProductById(connection, resultset.getLong("product_id"));
            products.add(product);
        }
        resultset.close();
        statement.close();
        return products;
    }
}
