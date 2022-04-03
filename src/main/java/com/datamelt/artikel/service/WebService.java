package com.datamelt.artikel.service;

import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.Product;
import com.datamelt.artikel.model.ProductContainer;
import com.datamelt.artikel.model.ProductOrigin;
import com.datamelt.artikel.port.RepositoryInterface;

import java.util.List;
import java.util.Map;

public class WebService
{
    private final RepositoryInterface repository;

    public WebService(RepositoryInterface respository)
    {
        this.repository = respository;
    }

    public List<Product> getAllProducts() throws Exception { return repository.getAllProducts(); }
    public Product getProductById(long id) throws Exception { return repository.getProductById(id); }
    public Product updateProduct(long id, Map<String,String> queryParamsMap) throws Exception
    {
        Producer producer = getProducerById(Long.parseLong(queryParamsMap.get("producer_id")));
        ProductContainer container = getProductContainerById(Long.parseLong(queryParamsMap.get("container_id")));
        ProductOrigin origin = getProductOriginById(Long.parseLong(queryParamsMap.get("origin_id")));

        Product product = new Product.ProductBuilder(queryParamsMap.get("number"), queryParamsMap.get("name"), producer)
                .id(id)
                .description(queryParamsMap.get("description"))
                .quantity(Integer.parseInt(queryParamsMap.get("quantity")))
                .weight(Double.parseDouble(queryParamsMap.get("weight")))
                .price(Double.parseDouble(queryParamsMap.get("price")))
                .container(container)
                .origin(origin)
                .build();
        repository.updateProduct(product);
        return product;
    }

    public Producer getProducerById(long id) throws Exception { return repository.getProducerById(id); }
    public List<Producer> getAllProducers() throws Exception { return repository.getAllProducers(); }
    public List<ProductContainer> getAllProductContainers() throws Exception { return repository.getAllProductContainers(); }
    public List<ProductOrigin> getAllProductOrigins() throws Exception { return repository.getAllProductOrigins(); }

    public ProductContainer getProductContainerById(long id) throws Exception { return repository.getProductContainerById(id); }
    public ProductOrigin getProductOriginById(long id) throws Exception { return repository.getProductOriginById(id); }
}
