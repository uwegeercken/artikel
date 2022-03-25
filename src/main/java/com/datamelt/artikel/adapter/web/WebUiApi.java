package com.datamelt.artikel.adapter.web;

import com.datamelt.artikel.model.Market;
import com.datamelt.artikel.model.Order;
import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.Product;
import com.datamelt.artikel.port.ApiInterface;
import com.datamelt.artikel.service.ControllerService;

import java.util.List;

public class WebUiApi implements ApiInterface
{
    private ControllerService service;

    public WebUiApi(ControllerService service) {
        this.service = service;
    }

    @Override
    public void addProduct(Product product)
    {
        service.addProduct(product);
    }

    @Override
    public void updateProduct(Product product)
    {
        service.updateProduct(product);
    }

    @Override
    public void removeProduct(long id)
    {
        service.removeProduct(id);
    }

    @Override
    public Product getProductById(long id) throws Exception
    {
        return service.getProductById(id);
    }

    @Override
    public Product getProductByName(String name) throws Exception
    {
        return service.getProductByName(name);
    }

    @Override
    public Product getProductByNumber(String number) throws Exception
    {
        return service.getProductByNumber(number);
    }

    @Override
    public List<Product> getAllProducts() throws Exception
    {
        return service.getAllProducts();
    }

    @Override
    public void addProducer(Producer producer) { service.addProducer(producer); }

    @Override
    public void updateProducer(Producer producer) { service.updateProducer(producer); }

    @Override
    public void removeProducer(long id) { service.removeProducer(id); }

    @Override
    public Producer getProducerById(long id) throws Exception
    {
        return service.getProducerById(id);
    }

    @Override
    public Producer getProducerByName(String name) throws Exception
    {
        return service.getProducerByName(name);
    }

    @Override
    public List<Producer> getAllProducers() throws Exception
    {
        return service.getAllProducers();
    }

    @Override
    public List<Market> getAllMarkets() throws Exception
    {
        return service.getAllMarkets();
    }

    @Override
    public List<Order> getAllOrders() throws Exception
    {
        return service.getAllOrders();
    }
}
