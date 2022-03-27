package com.datamelt.artikel.port;

import com.datamelt.artikel.model.*;

public interface FileInterface
{
    void addProduct(Product product);
    Product getProductById(long id) throws Exception;
    Product getProductByNumber(String number) throws Exception;
    boolean getExistProduct(String number) throws Exception;

    void addProductContainer(ProductContainer container);
    boolean getExistProductContainer(String name) throws Exception;

    void addProductOrigin(ProductOrigin origin);
    boolean getExistProductOrigin(String name) throws Exception;

    void addProducer(Producer producer);
    boolean getExistProducer(String name) throws Exception;
    Producer getProducerByName(String name) throws Exception;

    void addMarket(Market market);
    boolean getExistMarket(String name) throws Exception;

    void addOrder(Order order);
    Order getOrderByNumber(String number) throws Exception;
    boolean getExistOrder(String number) throws Exception;

    void addOrderItem(long orderId, long productId);
    boolean getExistOrderItem(long orderId, long productId) throws Exception;
}
