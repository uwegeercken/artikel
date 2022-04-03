package com.datamelt.artikel.service;

import com.datamelt.artikel.model.Market;
import com.datamelt.artikel.model.Order;
import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.Product;
import com.datamelt.artikel.port.RepositoryInterface;

import java.util.List;

public class ControllerService
{
    private final RepositoryInterface repository;

    public ControllerService(RepositoryInterface respository)
    {
        this.repository = respository;
    }

    public void addProduct(Product product) { repository.addProduct(product); }
    public void updateProduct(Product product) throws Exception { repository.updateProduct(product); }
    public void removeProduct(long id)
    {
        repository.removeProduct(id);
    }
    public Product getProductById(long id) throws Exception { return repository.getProductById(id); }
    public Product getProductByName(String name) throws Exception { return repository.getProductByName(name); }
    public Product getProductByNumber(String number) throws Exception { return repository.getProductByNumber(number); }
    public List<Product> getAllProducts() throws Exception { return repository.getAllProducts(); }

    public void addProducer(Producer producer) { repository.addProducer(producer); }
    public void updateProducer(Producer producer) { repository.updateProducer(producer); }
    public void removeProducer(long id)
    {
        repository.removeProducer(id);
    }
    public Producer getProducerById(long id) throws Exception { return repository.getProducerById(id); }
    public Producer getProducerByName(String name) throws Exception { return repository.getProducerByName(name); }
    public List<Producer> getAllProducers() throws Exception { return repository.getAllProducers(); }

    public List<Order> getAllOrders() throws Exception { return repository.getAllOrders(); }

    public List<Market> getAllMarkets() throws Exception { return repository.getAllMarkets(); }
}
