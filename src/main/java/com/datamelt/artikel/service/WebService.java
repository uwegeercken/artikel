package com.datamelt.artikel.service;

import com.datamelt.artikel.adapter.web.form.ProducerForm;
import com.datamelt.artikel.adapter.web.form.ProducerFormField;
import com.datamelt.artikel.adapter.web.form.ProductForm;
import com.datamelt.artikel.adapter.web.form.ProductFormField;
import com.datamelt.artikel.model.*;
import com.datamelt.artikel.port.RepositoryInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

public class WebService
{
    private static final Logger logger =  LoggerFactory.getLogger(WebService.class);
    private final RepositoryInterface repository;

    public WebService(RepositoryInterface respository)
    {
        this.repository = respository;
    }

    public List<Product> getAllProducts() throws Exception { return repository.getAllProducts(); }

    public Product getProductById(long id) throws Exception
    {
        return repository.getProductById(id);
    }

    public Product updateProduct(long id, ProductForm form) throws Exception
    {
        Producer producer = getProducerById(Long.parseLong(form.get(ProductFormField.PRODUCER_ID)));
        ProductContainer container = getProductContainerById(Long.parseLong(form.get(ProductFormField.CONTAINER_ID)));
        ProductOrigin origin = getProductOriginById(Long.parseLong(form.get(ProductFormField.ORIGIN_ID)));

        Product product = new Product(form.get(ProductFormField.NUMBER));
        product.setId(id);
        product.setName(form.get(ProductFormField.NAME));
        product.setDescription(form.get(ProductFormField.DESCRIPTION));
        product.setQuantity(Integer.parseInt(form.get(ProductFormField.QUANTITY)));
        product.setWeight(Double.parseDouble(form.get(ProductFormField.WEIGHT)));
        product.setPrice(Double.parseDouble(form.get(ProductFormField.PRICE)));
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

    public Product addProduct(ProductForm form) throws Exception
    {
        Producer producer = getProducerById(Long.parseLong(form.get(ProductFormField.PRODUCER_ID)));
        ProductContainer container = getProductContainerById(Long.parseLong(form.get(ProductFormField.CONTAINER_ID)));
        ProductOrigin origin = getProductOriginById(Long.parseLong(form.get(ProductFormField.ORIGIN_ID)));

        Product product = new Product(form.get(ProductFormField.NUMBER));
        product.setName(form.get(ProductFormField.NAME));
        product.setDescription(form.get(ProductFormField.DESCRIPTION));
        product.setQuantity(Integer.parseInt(form.get(ProductFormField.QUANTITY)));
        product.setWeight(Double.parseDouble(form.get(ProductFormField.WEIGHT)));
        product.setPrice(Double.parseDouble(form.get(ProductFormField.PRICE)));
        product.setContainer(container);
        product.setProducer(producer);
        product.setOrigin(origin);
        product.setTimestamp(new Date().getTime());
        repository.addProduct(product);
        return product;
    }

    public Producer updateProducer(long id, ProducerForm form) throws Exception
    {
        Producer producer = new Producer(form.get(ProducerFormField.NAME));
        producer.setId(id);
        producer.setNoOrdering(Integer.parseInt(form.get(ProducerFormField.NO_ORDERING)));
        try
        {
            repository.updateProducer(producer);
        }
        catch (Exception ex)
        {

            logger.error("Fehler beim ändern des Herstellers: [{}]", ex.getMessage());
        }
        return producer;
    }

    public Producer getProducerById(long id) throws Exception { return repository.getProducerById(id); }
    public boolean getExistProduct(String number) throws Exception { return repository.getExistProduct(number); }
    public List<Producer> getAllProducers() throws Exception { return repository.getAllProducers(); }
    public List<Market> getAllMarkets() throws Exception { return repository.getAllMarkets(); }
    public List<ProductContainer> getAllProductContainers() throws Exception { return repository.getAllProductContainers(); }
    public List<ProductOrigin> getAllProductOrigins() throws Exception { return repository.getAllProductOrigins(); }

    public ProductContainer getProductContainerById(long id) throws Exception { return repository.getProductContainerById(id); }
    public ProductOrigin getProductOriginById(long id) throws Exception { return repository.getProductOriginById(id); }

    public boolean getIsUniqueProduct(long id, String number) throws Exception { return repository.getIsUniqueProduct(id, number); }
    public boolean getIsUniqueProducer(long id, String name) throws Exception { return repository.getIsUniqueProducer(id, name); }

    public void createDatabaseTables() throws Exception { repository.createDatabaseTables(); }
}
