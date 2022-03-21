package com.datamelt.mogkdata.port;

import com.datamelt.mogkdata.model.Market;
import com.datamelt.mogkdata.model.Producer;
import com.datamelt.mogkdata.model.Product;

public interface FileInterface
{
    void addProduct(Product product);
    void addProducer(Producer producer);
    Producer getProducerByName(String name) throws Exception;
    void addMarket(Market market);
}
