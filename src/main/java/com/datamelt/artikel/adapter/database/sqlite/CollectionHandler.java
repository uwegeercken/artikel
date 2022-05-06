package com.datamelt.artikel.adapter.database.sqlite;

import com.datamelt.artikel.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

class CollectionHandler
{
    public static final String SQL_QUERY_PRODUCTS = "select * from product";
    public static final String SQL_QUERY_PRODUCERS = "select * from producer";
    public static final String SQL_QUERY_MARKETS = "select * from market";
    public static final String SQL_QUERY_CONTAINERS = "select * from productcontainer";
    public static final String SQL_QUERY_ORIGINS = "select * from productorigin";
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
            ProductContainer container = ProductContainerSearch.getProductContainerById(connection, resultset.getLong("productcontainer_id"));
            ProductOrigin origin = ProductOriginSearch.getProductOriginById(connection, resultset.getLong("productorigin_id"));

            Product product = new Product(resultset.getString("number"));
            product.setId(resultset.getLong("id"));
            product.setName(resultset.getString("name"));
            product.setDescription(resultset.getString("description"));
            product.setQuantity(resultset.getInt("quantity"));
            product.setWeight(resultset.getDouble("weight"));
            product.setPrice(resultset.getDouble("price"));
            product.setContainer(container);
            product.setProducer(producer);
            product.setOrigin(origin);
            product.setTimestamp(resultset.getLong("timestamp"));

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
            producer.setNoOrdering(resultset.getInt("no_ordering"));
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
            market.setType(resultset.getString("type"));
            markets.add(market);
        }
        resultset.close();
        statement.close();
        return markets;
    }

    public static List<ProductContainer> getAllProductContainers(Connection connection) throws Exception
    {
        List<ProductContainer> containers = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_CONTAINERS);
        ResultSet resultset = statement.executeQuery();
        while(resultset.next())
        {
            ProductContainer container = new ProductContainer(resultset.getString("name"));
            container.setId(resultset.getLong("id"));
            containers.add(container);
        }
        resultset.close();
        statement.close();
        return containers;
    }

    public static List<ProductOrigin> getAllProductOrigins(Connection connection) throws Exception
    {
        List<ProductOrigin> origins = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_ORIGINS);
        ResultSet resultset = statement.executeQuery();
        while(resultset.next())
        {
            ProductOrigin origin = new ProductOrigin(resultset.getString("name"));
            origin.setId(resultset.getLong("id"));
            origins.add(origin);
        }
        resultset.close();
        statement.close();
        return origins;
    }

    public static List<ProductOrder> getAllProductOrders(Connection connection) throws Exception
    {
        List<ProductOrder> orders = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_ORDERS);
        ResultSet resultset = statement.executeQuery();
        while(resultset.next())
        {
            ProductOrder order = new ProductOrder();
            order.setNumber(resultset.getString("number"));
            order.setTimestamp(resultset.getLong("timestamp"));
            order.setId(resultset.getLong("id"));
            order.setOrderItems(getAllProductOrderItems(connection, order));
            orders.add(order);
        }
        resultset.close();
        statement.close();
        return orders;
    }

    public static Map<Long, ProductOrderItem> getAllProductOrderItems(Connection connection, ProductOrder order) throws Exception
    {
        Map<Long, ProductOrderItem> items = new HashMap<>();
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_ORDER_ITEMS);
        statement.setLong(1, order.getId());
        ResultSet resultset = statement.executeQuery();
        while(resultset.next())
        {
            ProductOrderItem item = new ProductOrderItem();
            Product product = ProductSearch.getProductById(connection, resultset.getLong("product_id"));
            item.setProduct(product);
            item.setId(resultset.getLong("id"));
            item.setAmount(resultset.getInt("amount"));
            item.setTimestamp(resultset.getLong("timestamp"));

            items.put(item.getProduct().getId(), item);
        }
        resultset.close();
        statement.close();
        return items;
    }
}
