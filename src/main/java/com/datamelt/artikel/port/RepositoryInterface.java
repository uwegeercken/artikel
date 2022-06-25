package com.datamelt.artikel.port;

import com.datamelt.artikel.model.*;

import java.util.List;
import java.util.Map;

public interface RepositoryInterface
{
    void createDatabaseTables() throws Exception;
    void addProducer(Producer product);
    void updateProducer(Producer product);
    void deleteProducer(long id)throws Exception;
    Producer getProducerById(long id) throws Exception;
    Producer getProducerByName(String name) throws Exception;
    boolean getExistProducer(String name) throws Exception;
    List<Producer> getAllProducers() throws Exception;
    boolean getIsUniqueProducer(long id, String name) throws Exception;

    void addProduct(Product product);
    void updateProduct(Product product) throws Exception;
    Product getProductById(long id) throws Exception;
    Product getProductByName(String name) throws Exception;
    Product getProductByNumber(String number) throws Exception;
    boolean getExistProduct(String number) throws Exception;
    boolean getIsUniqueProduct(long id, String number) throws Exception;
    List<Product> getAllProducts(long producerId) throws Exception;
    void deleteProduct(long id) throws Exception;

    void addProductContainer(ProductContainer container);
    void updateProductContainer(ProductContainer container);
    void deleteProductContainer(long id);
    ProductContainer getProductContainerById(long id) throws Exception;
    ProductContainer getProductContainerByName(String name) throws Exception;
    boolean getExistProductContainer(String name) throws Exception;
    List<ProductContainer> getAllProductContainers() throws Exception;
    boolean getIsUniqueProductContainer(long id, String name) throws Exception;

    void addProductOrigin(ProductOrigin origin);
    void updateProductOrigin(ProductOrigin origin);
    void removeProductOrigin(long id);
    ProductOrigin getProductOriginById(long id) throws Exception;
    ProductOrigin getProductOriginByName(String name) throws Exception;
    boolean getExistProductOrigin(String name) throws Exception;
    List<ProductOrigin> getAllProductOrigins() throws Exception;
    boolean getIsUniqueProductOrigin(long id, String name) throws Exception;

    void addMarket(Market market);
    void updateMarket(Market market);
    void removeMarket(long id);
    Market getMarketById(long id) throws Exception;
    Market getMarketByName(String name) throws Exception;
    boolean getExistMarket(String name) throws Exception;
    List<Market> getAllMarkets() throws Exception;

    void addProductOrder(ProductOrder order);
    void updateOrder(ProductOrder order);
    void deleteProductOrder(long id) throws Exception;
    ProductOrder getOrderById(long id) throws Exception;
    ProductOrder getOrderByNumber(String number) throws Exception;
    boolean getExistOrder(String number) throws Exception;
    List<ProductOrder> getAllOrders() throws Exception;

    void addOrderItem(ProductOrderItem item);
    boolean getExistOrderItem(long orderId, long productId) throws Exception;
    void removeAllOrderItems(long orderId);

    User getUserByName(String name) throws Exception;
    void addUser(User user);
    boolean getExistUser(String name) throws Exception;

    List<ProductOrder> getAllProductOrders() throws Exception;

    ProductOrder getProductOrderById(long id) throws Exception;

    List<User> getAllUsers() throws Exception;

    long getAllProductsCount() throws Exception;
    Map<String,Long> getAllProducersProductsCount() throws Exception;
}
