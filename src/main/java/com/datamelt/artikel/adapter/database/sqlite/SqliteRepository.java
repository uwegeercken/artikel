package com.datamelt.artikel.adapter.database.sqlite;

import com.datamelt.artikel.model.Market;
import com.datamelt.artikel.model.Order;
import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.Product;
import com.datamelt.artikel.model.config.DatabaseConfiguration;
import com.datamelt.artikel.port.RepositoryInterface;

import java.sql.Connection;
import java.util.List;

public class SqliteRepository implements RepositoryInterface
{
    private final Connection connection;

    public SqliteRepository(DatabaseConfiguration configuration)
    {
        this.connection = SqliteConnection.getConnection(configuration);
    }

    @Override
    public void addProducer(Producer producer)
    {
        ProducerUpdate p = new ProducerUpdate(connection);
        p.addProducer(producer);
    }

    @Override
    public void updateProducer(Producer producer)
    {
        ProducerUpdate p = new ProducerUpdate(connection);
        p.updateProducer(producer);
    }

    @Override
    public void removeProducer(long id)
    {
        ProducerUpdate p = new ProducerUpdate(connection);
        p.removeProducer(id);
    }

    @Override
    public Producer getProducerById(long id) throws Exception
    {
        return ProducerSearch.getProducerById(connection, id);
    }

    @Override
    public Producer getProducerByName(String name) throws Exception
    {
        return ProducerSearch.getProducerByName(connection, name);
    }

    @Override
    public boolean getExistProducer(String name) throws Exception
    {
        return ProducerSearch.getExistProducer(connection, name);
    }

    @Override
    public List<Producer> getAllProducers() throws Exception
    {
        return CollectionHandler.getAllProducers(connection);
    }

    @Override
    public void addProduct(Product product)
    {
        ProductUpdate p = new ProductUpdate(connection);
        p.addProduct(product);
    }

    @Override
    public void updateProduct(Product product)
    {
        ProductUpdate p = new ProductUpdate(connection);
        p.updateProduct(product);
    }

    @Override
    public void removeProduct(long id)
    {
        ProductUpdate p = new ProductUpdate(connection);
        p.removeProduct(id);
    }

    @Override
    public Product getProductById(long id) throws Exception
    {
        return ProductSearch.getProductById(connection, id);
    }

    @Override
    public Product getProductByName(String name) throws Exception
    {
        return ProductSearch.getProductByName(connection, name);
    }

    @Override
    public Product getProductByNumber(String number) throws Exception
    {
        return ProductSearch.getProductByNumber(connection, number);
    }

    @Override
    public boolean getExistProduct(String number) throws Exception
    {
        return ProductSearch.getExistProduct(connection, number);
    }

    @Override
    public List<Product> getAllProducts() throws Exception
    {
        return CollectionHandler.getAllProducts(connection);
    }

    @Override
    public void addMarket(Market market)
    {
        MarketUpdate p = new MarketUpdate(connection);
        p.addMarket(market);
    }

    @Override
    public void updateMarket(Market market)
    {
        MarketUpdate p = new MarketUpdate(connection);
        p.updateMarket(market);
    }

    @Override
    public void removeMarket(long id)
    {
        MarketUpdate p = new MarketUpdate(connection);
        p.removeMarket(id);
    }

    @Override
    public Market getMarketById(long id) throws Exception
    {
        return MarketSearch.getMarketById(connection,id);
    }

    @Override
    public Market getMarketByName(String name) throws Exception
    {
        return MarketSearch.getMarketByName(connection,name);
    }

    @Override
    public boolean getExistMarket(String name) throws Exception
    {
        return MarketSearch.getExistMarket(connection, name);
    }

    @Override
    public List<Market> getAllMarkets() throws Exception
    {
        return CollectionHandler.getAllMarkets(connection);
    }

    @Override
    public void addOrder(Order order)
    {
        OrderUpdate p = new OrderUpdate(connection);
        p.addOrder(order);
    }

    @Override
    public void updateOrder(Order order)
    {
        OrderUpdate p = new OrderUpdate(connection);
        p.updateOrder(order);
    }

    @Override
    public void removeOrder(long id)
    {
        OrderUpdate p = new OrderUpdate(connection);
        p.removeOrder(id);
    }

    @Override
    public Order getOrderById(long id) throws Exception
    {
        return OrderSearch.getOrderById(connection,id);
    }

    @Override
    public Order getOrderByNumber(String number) throws Exception
    {
        return OrderSearch.getOrderByNumber(connection,number);
    }

    @Override
    public boolean getExistOrder(String number) throws Exception
    {
        return OrderSearch.getExistOrder(connection, number);
    }

    @Override
    public List<Order> getAllOrders() throws Exception
    {
        return CollectionHandler.getAllOrders(connection);
    }

    @Override
    public void addOrderItem(long orderId, long productId)
    {
        OrderItemUpdate p = new OrderItemUpdate(connection);
        p.addOrderItem(orderId,productId);
    }

    @Override
    public boolean getExistOrderItem(long orderId, long productId) throws Exception
    {
        return OrderItemSearch.getExistOrderItem(connection, orderId, productId);
    }

    @Override
    public void removeAllOrderItems(long orderId)
    {
        OrderItemUpdate p = new OrderItemUpdate(connection);
        p.removeAllOrderItems(orderId);
    }
}
