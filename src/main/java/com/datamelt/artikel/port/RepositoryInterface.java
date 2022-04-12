package com.datamelt.artikel.port;

import com.datamelt.artikel.model.*;

import java.util.List;

public interface RepositoryInterface
{
    void createDatabaseTables() throws Exception;
    void addProducer(Producer product);
    void updateProducer(Producer product);
    void removeProducer(long id);
    Producer getProducerById(long id) throws Exception;
    Producer getProducerByName(String name) throws Exception;
    boolean getExistProducer(String name) throws Exception;
    List<Producer> getAllProducers() throws Exception;
    boolean getIsUniqueProducer(long id, String name) throws Exception;

    void addProduct(Product product);
    void updateProduct(Product product) throws Exception;
    void removeProduct(long id);
    Product getProductById(long id) throws Exception;
    Product getProductByName(String name) throws Exception;
    Product getProductByNumber(String number) throws Exception;
    boolean getExistProduct(String number) throws Exception;
    boolean getIsUniqueProduct(long id, String number) throws Exception;
    List<Product> getAllProducts() throws Exception;

    void addProductContainer(ProductContainer container);
    void updateProductContainer(ProductContainer container);
    void removeProductContainer(long id);
    ProductContainer getProductContainerById(long id) throws Exception;
    ProductContainer getProductContainerByName(String name) throws Exception;
    boolean getExistProductContainer(String name) throws Exception;
    List<ProductContainer> getAllProductContainers() throws Exception;

    void addProductOrigin(ProductOrigin origin);
    void updateProductOrigin(ProductOrigin origin);
    void removeProductOrigin(long id);
    ProductOrigin getProductOriginById(long id) throws Exception;
    ProductOrigin getProductOriginByName(String name) throws Exception;
    boolean getExistProductOrigin(String name) throws Exception;
    List<ProductOrigin> getAllProductOrigins() throws Exception;

    void addMarket(Market market);
    void updateMarket(Market market);
    void removeMarket(long id);
    Market getMarketById(long id) throws Exception;
    Market getMarketByName(String name) throws Exception;
    boolean getExistMarket(String name) throws Exception;
    List<Market> getAllMarkets() throws Exception;

    void addOrder(Order order);
    void updateOrder(Order order);
    void removeOrder(long id);
    Order getOrderById(long id) throws Exception;
    Order getOrderByNumber(String number) throws Exception;
    boolean getExistOrder(String number) throws Exception;
    List<Order> getAllOrders() throws Exception;

    public void addOrderItem(long orderId, long productId);
    boolean getExistOrderItem(long orderId, long productId) throws Exception;
    public void removeAllOrderItems(long orderId);
}
