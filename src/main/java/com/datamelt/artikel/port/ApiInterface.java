package com.datamelt.artikel.port;

import com.datamelt.artikel.model.Market;
import com.datamelt.artikel.model.Order;
import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.Product;

import java.util.List;

public interface ApiInterface
{
    void addProduct(Product product);
    void updateProduct(Product product);
    void removeProduct(long id);
    Product getProductById(long id) throws Exception;
    Product getProductByName(String name) throws Exception;
    Product getProductByNumber(String number) throws Exception;
    List<Product> getAllProducts() throws Exception;

    void addProducer(Producer producer);
    void updateProducer(Producer producer);
    void removeProducer(long id);
    Producer getProducerById(long id) throws Exception;
    Producer getProducerByName(String name) throws Exception;
    List<Producer> getAllProducers() throws Exception;

    List<Market> getAllMarkets() throws Exception;
    List<Order> getAllOrders() throws Exception;
}
