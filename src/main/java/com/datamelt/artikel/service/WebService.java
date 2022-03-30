package com.datamelt.artikel.service;

import com.datamelt.artikel.model.Product;
import com.datamelt.artikel.port.RepositoryInterface;

public class WebService
{
    private final RepositoryInterface repository;

    public WebService(RepositoryInterface respository)
    {
        this.repository = respository;
    }

    public Product getProductById(long id) throws Exception { return repository.getProductById(id); }
}
