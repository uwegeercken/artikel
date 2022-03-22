package com.datamelt.artikel.service;

import com.datamelt.artikel.model.Market;
import com.datamelt.artikel.model.Order;
import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.Product;
import com.datamelt.artikel.port.RepositoryInterface;

public class LoaderService
{
    private RepositoryInterface repository;

    public LoaderService(RepositoryInterface repository)
    {
        this.repository = repository;
    }

    public void addProduct(Product product) { repository.addProduct(product); }

    public void addProducer(Producer producer) { repository.addProducer(producer); }

    public Producer getProducerByName(String name) throws Exception { return repository.getProducerByName(name); }

    public void addMarket(Market market) { repository.addMarket(market); }

    public void addOrder(Order order) { repository.addOrder(order); }

    public void addOrderItem(long orderId, long productId) { repository.addOrderItem(orderId, productId); }

    public void removeAllOrderItems(long orderId) { repository.removeAllOrderItems(orderId); }
}
