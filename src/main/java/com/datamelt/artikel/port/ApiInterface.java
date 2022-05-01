package com.datamelt.artikel.port;

import com.datamelt.artikel.model.Market;
import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.Product;
import com.datamelt.artikel.model.ProductOrder;
import org.apache.logging.log4j.core.config.Order;

import java.util.List;

public interface ApiInterface
{
    void addProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(long id) throws Exception;
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
    List<ProductOrder> getAllOrders() throws Exception;
}
