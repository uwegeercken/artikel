package com.datamelt.artikel.service;

import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.Product;
import com.datamelt.artikel.port.RepositoryInterface;

import java.util.List;

public class WebService
{
    private final RepositoryInterface repository;

    public WebService(RepositoryInterface respository)
    {
        this.repository = respository;
    }

    public List<Product> getAllProducts() throws Exception { return repository.getAllProducts(); }

    public List<Producer> getAllProducers() throws Exception { return repository.getAllProducers(); }
}
