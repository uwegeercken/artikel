package com.datamelt.mogkdata.port;

import com.datamelt.mogkdata.model.Market;
import com.datamelt.mogkdata.model.Producer;
import com.datamelt.mogkdata.model.Product;

import java.util.List;

public interface RepositoryInterface
{
    void addProducer(Producer product);
    void updateProducer(Producer product);
    void removeProducer(long id);
    Producer getProducerById(long id) throws Exception;
    Producer getProducerByName(String name) throws Exception;
    List<Producer> getAllProducers() throws Exception;

    void addProduct(Product product);
    void updateProduct(Product product);
    void removeProduct(long id);
    Product getProductById(long id) throws Exception;
    Product getProductByName(String name) throws Exception;
    Product getProductByNumber(String number) throws Exception;
    List<Product> getAllProducts() throws Exception;

    void addMarket(Market market);
    void updateMarket(Market market);
    void removeMarket(long id);
    Market getMarketById(long id) throws Exception;
    Market getMarketByName(String name) throws Exception;
    List<Market> getAllMarkets() throws Exception;
}
