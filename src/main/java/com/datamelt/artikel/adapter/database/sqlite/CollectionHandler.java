package com.datamelt.artikel.adapter.database.sqlite;

import com.datamelt.artikel.model.*;
import com.datamelt.artikel.port.CollectionHandlerInterface;
import com.datamelt.artikel.util.CalendarUtility;
import com.datamelt.artikel.util.FileUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

class CollectionHandler implements CollectionHandlerInterface
{
    public static final String SQL_QUERY_PRODUCER_PRODUCTS = "select * from product where producer_id=? and timestamp < ? and timestamp > ? order by cast(number as int)";
    public static final String SQL_QUERY_PRODUCER_AVAILABLE_PRODUCTS = "select * from product where producer_id=? and timestamp < ? and timestamp > ? and unavailable=0 order by cast(number as int)";
    public static final String SQL_QUERY_CHANGED_PRODUCTS = "select * from product where producer_id=? and timestamp < ? order by cast(number as int)";
    public static final String SQL_QUERY_PRODUCERS = "select * from producer order by id";
    public static final String SQL_QUERY_MARKETS = "select * from market order by id";
    public static final String SQL_QUERY_CONTAINERS = "select * from productcontainer order by id";
    public static final String SQL_QUERY_ORIGINS = "select * from productorigin order by id";
    public static final String SQL_QUERY_ORDERS = "select * from productorder order by timestamp_created_date desc";
    public static final String SQL_QUERY_ORDER_ITEMS = "select * from productorder_item where productorder_id=?";
    public static final String SQL_QUERY_USERS = "select * from user order by name";
    public static final String SQL_QUERY_PRODUCT_HISTORY = "select * from product_history where product_id=? order by timestamp";

    public static final String SQL_QUERY_PRODUCTS_COUNT = "select count(1) as counter from product";
    public static final String SQL_QUERY_PRODUCERS_PRODUCTS_COUNT = "select producer.name as name, count(1) as counter from product,producer where producer.id=product.producer_id group by producer_id order by producer.name";

    private static final Logger logger = LoggerFactory.getLogger(CollectionHandler.class);

    @Override
    public long getAllProductsCount(Connection connection) throws Exception
    {
        long numberOfProducts = 0;
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_PRODUCTS_COUNT);

        ResultSet resultset = statement.executeQuery();
        if(resultset.next())
        {
            numberOfProducts = resultset.getLong("counter");
        }
        return numberOfProducts;
    }

    @Override
    public Map<String,Long> getAllProducersProductsCount(Connection connection) throws Exception
    {
        Map<String,Long> productCounts = new HashMap<>();
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_PRODUCERS_PRODUCTS_COUNT);
        ResultSet resultset = statement.executeQuery();
        while(resultset.next())
        {
            productCounts.put(resultset.getString("name"), resultset.getLong("counter"));
        }
        return productCounts;
    }

    @Override
    public List<Product> getAllProducts(Connection connection, long producerId, boolean availableOnly, int changedSinceNumberOfDaysMin, int changedSinceNumberOfDaysMax) throws Exception
    {
        long searchProductsBefore = CalendarUtility.getTimestamp(changedSinceNumberOfDaysMin);
        long searchProductsUntil = CalendarUtility.getTimestamp(changedSinceNumberOfDaysMax);
        List<Product> products = new ArrayList<>();
        PreparedStatement statement;
        if(availableOnly)
        {
            statement = connection.prepareStatement(SQL_QUERY_PRODUCER_AVAILABLE_PRODUCTS);
        }
        else
        {
            statement = connection.prepareStatement(SQL_QUERY_PRODUCER_PRODUCTS);
        }
        statement.setLong(1, producerId);
        statement.setLong(2, searchProductsBefore);
        statement.setLong(3, searchProductsUntil);

        ResultSet resultset = statement.executeQuery();
        while(resultset.next())
        {
            Producer producer = ProducerSearch.getProducerById(connection, resultset.getLong("producer_id"));
            ProductContainer container = ProductContainerSearch.getProductContainerById(connection, resultset.getLong("productcontainer_id"));
            ProductOrigin origin = ProductOriginSearch.getProductOriginById(connection, resultset.getLong("productorigin_id"));

            Product product = new Product(resultset.getString("number"));
            product.setId(resultset.getLong("id"));
            product.setName(resultset.getString("name"));
            product.setTitle(resultset.getString("title"));
            product.setSubtitle(resultset.getString("subtitle"));
            product.setQuantity(resultset.getInt("quantity"));
            product.setWeight(resultset.getDouble("weight"));
            product.setPrice(resultset.getDouble("price"));
            product.setUnavailable(resultset.getInt("unavailable"));
            if(container!=null)
            {
                product.setContainer(container);
            }
            else
            {
                logger.error("the requested container could not be found. id [{}]", resultset.getLong("productcontainer_id"));
            }
            if(producer!=null)
            {
                product.setProducer(producer);
            }
            else
            {
                logger.error("the requested producer could not be found. id [{}]", resultset.getLong("producer_id"));
            }
            if(origin!=null)
            {
                product.setOrigin(origin);
            }
            else
            {
                logger.error("the requested product origin could not be found. id [{}]", resultset.getLong("productorigin_id"));
            }
            product.setTimestamp(resultset.getLong("timestamp"));

            products.add(product);
        }
        resultset.close();
        statement.close();
        return products;
    }


    @Override
    public List<Product> getChangedProducts(Connection connection, long producerId, int changedSinceNumberOfDays) throws Exception
    {
        long searchProductsBefore = CalendarUtility.getTimestamp(changedSinceNumberOfDays);
        List<Product> products = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_CHANGED_PRODUCTS);
        statement.setLong(1, producerId);
        statement.setLong(2, searchProductsBefore);

        ResultSet resultset = statement.executeQuery();
        while(resultset.next())
        {
            Producer producer = ProducerSearch.getProducerById(connection, resultset.getLong("producer_id"));
            ProductContainer container = ProductContainerSearch.getProductContainerById(connection, resultset.getLong("productcontainer_id"));
            ProductOrigin origin = ProductOriginSearch.getProductOriginById(connection, resultset.getLong("productorigin_id"));

            Product product = new Product(resultset.getString("number"));
            product.setId(resultset.getLong("id"));
            product.setName(resultset.getString("name"));
            product.setTitle(resultset.getString("title"));
            product.setSubtitle(resultset.getString("subtitle"));
            product.setQuantity(resultset.getInt("quantity"));
            product.setWeight(resultset.getDouble("weight"));
            product.setPrice(resultset.getDouble("price"));
            product.setUnavailable(resultset.getInt("unavailable"));
            if(container!=null)
            {
                product.setContainer(container);
            }
            else
            {
                logger.error("the requested container could not be found. id [{}]", resultset.getLong("productcontainer_id"));
            }
            if(producer!=null)
            {
                product.setProducer(producer);
            }
            else
            {
                logger.error("the requested producer could not be found. id [{}]", resultset.getLong("producer_id"));
            }
            if(origin!=null)
            {
                product.setOrigin(origin);
            }
            else
            {
                logger.error("the requested product origin could not be found. id [{}]", resultset.getLong("productorigin_id"));
            }
            product.setTimestamp(resultset.getLong("timestamp"));

            products.add(product);
        }
        resultset.close();
        statement.close();
        return products;
    }

    @Override
    public List<Producer> getAllProducers(Connection connection) throws Exception
    {
        List<Producer> producers = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_PRODUCERS);
        ResultSet resultset = statement.executeQuery();
        while(resultset.next())
        {
            Producer producer = new Producer(resultset.getString("name"));
            producer.setId(resultset.getLong("id"));
            producer.setNoOrdering(resultset.getInt("no_ordering"));
            producer.setEmailAddress(resultset.getString("email_address"));
            producers.add(producer);
        }
        resultset.close();
        statement.close();
        return producers;
    }

    @Override
    public List<Market> getAllMarkets(Connection connection) throws Exception
    {
        List<Market> markets = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_MARKETS);
        ResultSet resultset = statement.executeQuery();
        while(resultset.next())
        {
            Market market = new Market(resultset.getString("name"));
            market.setId(resultset.getLong("id"));
            market.setType(resultset.getLong("type"));
            markets.add(market);
        }
        resultset.close();
        statement.close();
        return markets;
    }

    @Override
    public List<ProductContainer> getAllProductContainers(Connection connection) throws Exception
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

    @Override
    public List<ProductOrigin> getAllProductOrigins(Connection connection) throws Exception
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

    @Override
    public List<ProductOrder> getAllProductOrders(Connection connection) throws Exception
    {
        List<ProductOrder> orders = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_ORDERS);
        ResultSet resultset = statement.executeQuery();
        while(resultset.next())
        {
            ProductOrder order = new ProductOrder();
            order.setNumber(resultset.getString("number"));
            order.setProducerId(resultset.getLong("producer_id"));
            order.setProducer(ProducerSearch.getProducerById(connection, resultset.getLong("producer_id")));
            order.setTimestampCreatedDate(resultset.getLong("timestamp_created_date"));
            order.setTimestampOrderDate(resultset.getLong("timestamp_order_date"));
            order.setTimestampEmailSent(resultset.getLong("timestamp_email_sent"));
            order.setId(resultset.getLong("id"));
            order.setOrderItems(getAllProductOrderItems(connection, order.getId()));
            orders.add(order);
        }
        resultset.close();
        statement.close();
        return orders;
    }

    @Override
    public Map<Long, ProductOrderItem> getAllProductOrderItems(Connection connection, long orderId) throws Exception
    {
        Map<Long, ProductOrderItem> items = new HashMap<>();
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_ORDER_ITEMS);
        statement.setLong(1,orderId);
        ResultSet resultset = statement.executeQuery();
        while(resultset.next())
        {
            ProductOrderItem item = new ProductOrderItem();
            Product product = ProductSearch.getProductById(connection, resultset.getLong("product_id"));
            if(product!=null)
            {
                item.setProduct(product);
                item.setId(resultset.getLong("id"));
                item.setAmount(resultset.getInt("amount"));
                item.setTimestamp(resultset.getLong("timestamp"));
                items.put(item.getProduct().getId(), item);
            }
            else
            {
                logger.error("the requested product could not be found. product order id [{}], product id [{}]", orderId, resultset.getLong("product_id"));
            }
        }
        resultset.close();
        statement.close();
        return items;
    }

    @Override
    public List<User> getAllUsers(Connection connection) throws Exception
    {
        List<User> users = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_USERS);
        ResultSet resultset = statement.executeQuery();
        while(resultset.next())
        {
            User user = new User(resultset.getString("name"));
            user.setId(resultset.getLong("id"));
            user.setFullName(resultset.getString("full_name"));
            user.setRole(resultset.getString("role"));
            users.add(user);
        }
        resultset.close();
        statement.close();
        return users;
    }

    @Override
    public List<ProductHistory> getProductHistory(Connection connection, Product product) throws Exception
    {
        List<ProductHistory> fullHistory = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_PRODUCT_HISTORY);
        statement.setLong(1, product.getId());
        ResultSet resultset = statement.executeQuery();
        while(resultset.next())
        {
            ProductHistory history = new ProductHistory(product.getId());
            history.setId(resultset.getLong("id"));
            history.setPrice(resultset.getFloat("price"));
            history.setTimestamp(resultset.getLong("timestamp"));
            fullHistory.add(history);
        }
        resultset.close();
        statement.close();
        return fullHistory;
    }
}
