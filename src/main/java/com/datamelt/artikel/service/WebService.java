package com.datamelt.artikel.service;

import com.datamelt.artikel.adapter.web.form.ProducerForm;
import com.datamelt.artikel.adapter.web.form.ProducerFormField;
import com.datamelt.artikel.adapter.web.form.ProductForm;
import com.datamelt.artikel.adapter.web.form.ProductFormField;
import com.datamelt.artikel.model.*;
import com.datamelt.artikel.port.RepositoryInterface;
import com.datamelt.artikel.port.WebServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class WebService implements WebServiceInterface
{
    private static final Logger logger =  LoggerFactory.getLogger(WebService.class);
    private final RepositoryInterface repository;

    public WebService(RepositoryInterface respository)
    {
        this.repository = respository;
    }

    @Override
    public List<Product> getAllProducts(long producerId) throws Exception { return repository.getAllProducts(producerId); }

    @Override
    public Product getProductById(long id) throws Exception
    {
        return repository.getProductById(id);
    }

    @Override
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
            logger.debug("updating product - number: [{}]", product.getNumber());
            repository.updateProduct(product);
        }
        catch (Exception ex)
        {

            logger.error("error updating product: [{}]", ex.getMessage());
        }
        return product;
    }

    @Override
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
        try
        {
            logger.debug("adding product - number: [{}]", product.getNumber());
            repository.addProduct(product);
        }
        catch (Exception ex)
        {
            logger.error("error adding product: [{}]", ex.getMessage());
        }
        return product;
    }

    @Override
    public Producer updateProducer(long id, ProducerForm form) throws Exception
    {
        Producer producer = new Producer(form.get(ProducerFormField.NAME));
        producer.setId(id);
        producer.setNoOrdering(Integer.parseInt(form.get(ProducerFormField.NO_ORDERING)));
        try
        {
            logger.debug("updating producer - name: [{}]", producer.getName());
            repository.updateProducer(producer);
        }
        catch (Exception ex)
        {

            logger.error("error adding producer: [{}]", ex.getMessage());
        }
        return producer;
    }

    @Override
    public Producer addProducer(ProducerForm form) throws Exception
    {
        Producer producer = new Producer(form.get(ProducerFormField.NAME));
        producer.setName(form.get(ProducerFormField.NAME));
        producer.setNoOrdering(Integer.parseInt(form.get(ProducerFormField.NO_ORDERING)));
        try
        {
            logger.debug("adding producer - name: [{}]", producer.getName());
            repository.addProducer(producer);
        }
        catch (Exception ex)
        {
            logger.error("error adding producer: [{}]", ex.getMessage());
        }
        return producer;
    }

    @Override
    public Producer getProducerById(long id) throws Exception { return repository.getProducerById(id); }
    @Override
    public boolean getExistProduct(String number) throws Exception { return repository.getExistProduct(number); }
    @Override
    public List<Producer> getAllProducers() throws Exception { return repository.getAllProducers(); }
    @Override
    public List<Market> getAllMarkets() throws Exception { return repository.getAllMarkets(); }
    @Override
    public List<ProductContainer> getAllProductContainers() throws Exception { return repository.getAllProductContainers(); }
    @Override
    public List<ProductOrigin> getAllProductOrigins() throws Exception { return repository.getAllProductOrigins(); }

    @Override
    public ProductContainer getProductContainerById(long id) throws Exception { return repository.getProductContainerById(id); }
    @Override
    public ProductOrigin getProductOriginById(long id) throws Exception { return repository.getProductOriginById(id); }

    @Override
    public boolean getIsUniqueProduct(long id, String number) throws Exception { return repository.getIsUniqueProduct(id, number); }
    @Override
    public boolean getIsUniqueProducer(long id, String name) throws Exception { return repository.getIsUniqueProducer(id, name); }

    @Override
    public void createDatabaseTables() throws Exception { repository.createDatabaseTables(); }

    @Override
    public void deleteProduct(long id) throws Exception
    {
        repository.deleteProduct(id);
    }

    @Override
    public void deleteProducer(long id) throws Exception
    {
        repository.deleteProducer(id);
    }

    @Override
    public User getUserByName(String name) throws Exception
    {
        return repository.getUserByName(name);
    }

    @Override
    public Map<Long, ProductOrderItem> getShopProductOrderItems(ProductOrder order) throws Exception
    {
        return order.getOrderItems();
    }

    @Override
    public void addProductOrder(ProductOrder order)
    {
        repository.addProductOrder(order);
    }

    @Override
    public List<ProductOrder> getAllProductOrders() throws Exception
    {
        return repository.getAllProductOrders();
    }

    @Override
    public ProductOrder getProductOrderById(long id) throws Exception
    {
        return repository.getProductOrderById(id);
    }
}
