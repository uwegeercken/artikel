package com.datamelt.artikel.port;

import com.datamelt.artikel.model.Market;
import com.datamelt.artikel.model.Order;
import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.Product;

public interface FileInterface
{
    void addProduct(Product product);
    void addProducer(Producer producer);
    Producer getProducerByName(String name) throws Exception;
    void addMarket(Market market);
    void addOrder(Order order);
    public void addOrderItem(long orderId, long productId);
}
