package com.datamelt.artikel.service;

import com.datamelt.artikel.adapter.csv.CsvLoader;
import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.Product;
import com.datamelt.artikel.model.ProductContainer;
import com.datamelt.artikel.model.ProductOrigin;
import com.datamelt.artikel.port.RepositoryInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class WebService
{
    private static final Logger logger = LogManager.getLogger(CsvLoader.class);
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

        Product product = new Product(queryParamsMap.get("number"));
        product.setId(id);
        product.setName(queryParamsMap.get("name"));
        product.setDescription(queryParamsMap.get("description"));
        product.setQuantity(Integer.parseInt(queryParamsMap.get("quantity")));
        product.setWeight(Double.parseDouble(queryParamsMap.get("weight")));
        product.setPrice(Double.parseDouble(queryParamsMap.get("price")));
        product.setContainer(container);
        product.setProducer(producer);
        product.setOrigin(origin);
        product.setTimestamp(new Date().getTime());
        try
        {
            repository.updateProduct(product);
        }
        catch (Exception ex)
        {

            logger.error("Fehler beim ändern des Produktes: [{}]", ex.getMessage());
        }
        return product;
    }

    public Product addProduct(Map<String,String> queryParamsMap) throws Exception
    {
        Producer producer = getProducerById(Long.parseLong(queryParamsMap.get("producer_id")));
        ProductContainer container = getProductContainerById(Long.parseLong(queryParamsMap.get("container_id")));
        ProductOrigin origin = getProductOriginById(Long.parseLong(queryParamsMap.get("origin_id")));

        Product product = new Product(queryParamsMap.get("number"));
        product.setName(queryParamsMap.get("name"));
        product.setDescription(queryParamsMap.get("description"));
        product.setQuantity(Integer.parseInt(queryParamsMap.get("quantity")));
        product.setWeight(Double.parseDouble(queryParamsMap.get("weight")));
        product.setPrice(Double.parseDouble(queryParamsMap.get("price")));
        product.setContainer(container);
        product.setProducer(producer);
        product.setOrigin(origin);
        product.setTimestamp(new Date().getTime());
        repository.addProduct(product);
        return product;
    }

    public Producer getProducerById(long id) throws Exception { return repository.getProducerById(id); }
    public List<Producer> getAllProducers() throws Exception { return repository.getAllProducers(); }
    public List<ProductContainer> getAllProductContainers() throws Exception { return repository.getAllProductContainers(); }
    public List<ProductOrigin> getAllProductOrigins() throws Exception { return repository.getAllProductOrigins(); }

    public ProductContainer getProductContainerById(long id) throws Exception { return repository.getProductContainerById(id); }
    public ProductOrigin getProductOriginById(long id) throws Exception { return repository.getProductOriginById(id); }
}
