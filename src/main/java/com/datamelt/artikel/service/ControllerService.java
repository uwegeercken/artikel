package com.datamelt.artikel.service;

import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.Product;
import com.datamelt.artikel.port.RepositoryInterface;

import java.util.List;

public class ControllerService
{
    private final RepositoryInterface respository;

    public ControllerService(RepositoryInterface respository)
    {
        this.respository = respository;
    }

    public void addProduct(Product product) { respository.addProduct(product); }
    public void updateProduct(Product product) { respository.updateProduct(product); }
    public void removeProduct(long id)
    {
        respository.removeProduct(id);
    }
    public Product getProductById(long id) throws Exception { return respository.getProductById(id); }
    public Product getProductByName(String name) throws Exception { return respository.getProductByName(name); }
    public Product getProductByNumber(String number) throws Exception { return respository.getProductByNumber(number); }
    public List<Product> getAllProducts() throws Exception { return respository.getAllProducts(); }

    public void addProducer(Producer producer) { respository.addProducer(producer); }
    public void updateProducer(Producer producer) { respository.updateProducer(producer); }
    public void removeProducer(long id)
    {
        respository.removeProducer(id);
    }
    public Producer getProducerById(long id) throws Exception { return respository.getProducerById(id); }
    public Producer getProducerByName(String name) throws Exception { return respository.getProducerByName(name); }
    public List<Producer> getAllProducers() throws Exception { return respository.getAllProducers(); }

}
