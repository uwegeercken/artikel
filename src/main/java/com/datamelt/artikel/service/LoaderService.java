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

    public Product getProductById(long id) throws Exception { return repository.getProductById(id); }

    public boolean getExistProduct(String number) throws Exception { return repository.getExistProduct(number); }

    public void addProducer(Producer producer) { repository.addProducer(producer); }

    public Producer getProducerByName(String name) throws Exception { return repository.getProducerByName(name); }

    public boolean getExistProducer(String name) throws Exception { return repository.getExistProducer(name); }

    public void addMarket(Market market) { repository.addMarket(market); }

    public boolean getExistMarket(String name) throws Exception { return repository.getExistMarket(name); }

    public void addOrder(Order order) { repository.addOrder(order); }

    public Order getOrderById(long id) throws Exception { return repository.getOrderById(id); }

    public boolean getExistOrder(String number) throws Exception { return repository.getExistOrder(number); }

    public void addOrderItem(long orderId, long productId) { repository.addOrderItem(orderId, productId); }

    public boolean getExistOrderItem(long orderId, long productId) throws Exception { return repository.getExistOrderItem(orderId,productId); }

    public void removeAllOrderItems(long orderId) { repository.removeAllOrderItems(orderId); }
}
