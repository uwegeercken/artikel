package com.datamelt.artikel.service;

import com.datamelt.artikel.model.*;
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

    public Product getProductByNumber(String number) throws Exception { return repository.getProductByNumber(number); }

    public boolean getExistProduct(String number) throws Exception { return repository.getExistProduct(number); }

    public void addProductContainer(ProductContainer container) { repository.addProductContainer(container); }

    public ProductContainer getProducContainerByName(String name) throws Exception { return repository.getProductContainerByName(name); }

    public boolean getExistProductContainer(String name) throws Exception { return repository.getExistProductContainer(name); }

    public void addProductOrigin(ProductOrigin origin) { repository.addProductOrigin(origin); }

    public ProductOrigin getProducOriginByName(String name) throws Exception { return repository.getProductOriginByName(name); }

    public boolean getExistProductOrigin(String name) throws Exception { return repository.getExistProductOrigin(name); }

    public void addProducer(Producer producer) { repository.addProducer(producer); }

    public Producer getProducerByName(String name) throws Exception { return repository.getProducerByName(name); }

    public boolean getExistProducer(String name) throws Exception { return repository.getExistProducer(name); }

    public void addMarket(Market market) { repository.addMarket(market); }

    public boolean getExistMarket(String name) throws Exception { return repository.getExistMarket(name); }

    public void addProductOrder(ProductOrder order) { repository.addProductOrder(order); }

    public ProductOrder getOrderByNumber(String number) throws Exception { return repository.getOrderByNumber(number); }

    public boolean getExistOrder(String number) throws Exception { return repository.getExistOrder(number); }

    public void addOrderItem(long orderId, long productId, int amount) { repository.addOrderItem(orderId, productId, amount); }

    public boolean getExistOrderItem(long orderId, long productId) throws Exception { return repository.getExistOrderItem(orderId,productId); }

    public void removeAllOrderItems(long orderId) { repository.removeAllOrderItems(orderId); }

    public void addUser(User user) { repository.addUser(user); }

    public boolean getExistUser(String name) throws Exception { return repository.getExistUser(name); }
}
