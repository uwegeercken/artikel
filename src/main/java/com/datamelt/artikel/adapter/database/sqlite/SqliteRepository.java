package com.datamelt.artikel.adapter.database.sqlite;

import com.datamelt.artikel.model.*;
import com.datamelt.artikel.config.DatabaseConfiguration;
import com.datamelt.artikel.port.RepositoryInterface;

import java.sql.Connection;
import java.util.List;

public class SqliteRepository implements RepositoryInterface
{
    private final Connection connection;

    public SqliteRepository(DatabaseConfiguration configuration) throws Exception
    {
        this.connection = SqliteConnection.getConnection(configuration);
    }


    @Override
    public void createDatabaseTables() throws Exception
    {
        SqliteTable.createTables(connection);
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
    public void deleteProducer(long id) throws Exception
    {
        ProducerUpdate p = new ProducerUpdate(connection);
        p.deleteProducer(id);
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
    public boolean getIsUniqueProducer(long id, String name) throws Exception
    {
        return ProducerSearch.getIsUniqueProducer(connection, id, name);
    }

    @Override
    public void addProduct(Product product)
    {
        ProductUpdate p = new ProductUpdate(connection);
        p.addProduct(product);
    }

    @Override
    public void updateProduct(Product product) throws Exception
    {
        ProductUpdate p = new ProductUpdate(connection);
        p.updateProduct(product);
    }

    @Override
    public void deleteProduct(long id) throws Exception
    {
        ProductUpdate p = new ProductUpdate(connection);
        p.deleteProduct(id);
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
    public boolean getIsUniqueProduct(long id, String number) throws Exception
    {
        return ProductSearch.getIsUniqueProduct(connection, id, number);
    }

    @Override
    public List<Product> getAllProducts() throws Exception
    {
        return CollectionHandler.getAllProducts(connection);
    }

    @Override
    public void addProductContainer(ProductContainer container)
    {
        ProductContainerUpdate p = new ProductContainerUpdate(connection);
        p.addProductContainer(container);
    }

    @Override
    public void updateProductContainer(ProductContainer container)
    {
        ProductContainerUpdate p = new ProductContainerUpdate(connection);
        p.updateProductContainer(container);
    }

    @Override
    public void removeProductContainer(long id)
    {
        ProductContainerUpdate p = new ProductContainerUpdate(connection);
        p.removeProductContainer(id);
    }

    @Override
    public ProductContainer getProductContainerById(long id) throws Exception
    {
        return ProductContainerSearch.getProductContainerById(connection, id);
    }

    @Override
    public ProductContainer getProductContainerByName(String name) throws Exception
    {
        return ProductContainerSearch.getProductContainerByName(connection, name);
    }

    @Override
    public boolean getExistProductContainer(String name) throws Exception
    {
        return ProductContainerSearch.getExistProductContainer(connection,name);
    }

    @Override
    public List<ProductContainer> getAllProductContainers() throws Exception
    {
        return CollectionHandler.getAllProductContainers(connection);
    }

    @Override
    public void addProductOrigin(ProductOrigin origin)
    {
        ProductOriginUpdate p = new ProductOriginUpdate(connection);
        p.addProductOrigin(origin);
    }

    @Override
    public void updateProductOrigin(ProductOrigin origin)
    {
        ProductOriginUpdate p = new ProductOriginUpdate(connection);
        p.updateProductOrigin(origin);
    }

    @Override
    public void removeProductOrigin(long id)
    {
        ProductOriginUpdate p = new ProductOriginUpdate(connection);
        p.removeProductOrigin(id);
    }

    @Override
    public ProductOrigin getProductOriginById(long id) throws Exception
    {
        return ProductOriginSearch.getProductOriginById(connection, id);
    }

    @Override
    public ProductOrigin getProductOriginByName(String name) throws Exception
    {
        return ProductOriginSearch.getProductOriginByName(connection, name);
    }

    @Override
    public boolean getExistProductOrigin(String name) throws Exception
    {
        return ProductOriginSearch.getExistProductCOrigin(connection,name);
    }

    @Override
    public List<ProductOrigin> getAllProductOrigins() throws Exception
    {
        return CollectionHandler.getAllProductOrigins(connection);
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

    @Override
    public User getUserByName(String name) throws Exception
    {
        return UserSearch.getUserByName(connection, name);
    }
}
