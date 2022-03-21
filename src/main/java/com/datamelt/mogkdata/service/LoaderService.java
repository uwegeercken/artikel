package com.datamelt.mogkdata.service;

import com.datamelt.mogkdata.model.Market;
import com.datamelt.mogkdata.model.Producer;
import com.datamelt.mogkdata.model.Product;
import com.datamelt.mogkdata.port.RepositoryInterface;

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
}
