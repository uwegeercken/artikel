package com.datamelt.artikel.port;

import com.datamelt.artikel.model.Market;
import com.datamelt.artikel.model.Order;
import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.Product;

public interface FileInterface
{
    void addProduct(Product product);
    Product getProductById(long id) throws Exception;
    boolean getExistProduct(String number) throws Exception;
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
