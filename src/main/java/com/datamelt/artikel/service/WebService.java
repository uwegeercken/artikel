package com.datamelt.artikel.service;

import com.datamelt.artikel.adapter.opa.model.OpaAcl;
import com.datamelt.artikel.adapter.opa.model.OpaAclUrl;
import com.datamelt.artikel.adapter.opa.model.OpaInput;
import com.datamelt.artikel.adapter.opa.model.OpaValidationResult;
import com.datamelt.artikel.adapter.web.form.*;
import com.datamelt.artikel.app.web.util.Endpoints;
import com.datamelt.artikel.app.web.util.NumberFormatter;
import com.datamelt.artikel.config.MainConfiguration;
import com.datamelt.artikel.model.*;
import com.datamelt.artikel.port.*;
import com.datamelt.artikel.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class WebService implements WebServiceInterface, CsvWriterInterface
{
    private static final Logger logger =  LoggerFactory.getLogger(WebService.class);
    private final RepositoryInterface repository;
    private final EmailApiInterface email;
    private final CsvWriterInterface csvLabelWriter;
    private OrderDocumentInterface orderDocumentGenerator;
    private OpaApiInterface opaClient;

    public WebService(RepositoryInterface respository, CsvWriterInterface csvLabelWriter, OrderDocumentInterface orderDocumentGenerator, EmailApiInterface email, OpaApiInterface opaClient) throws Exception
    {
        this.repository = respository;
        this.csvLabelWriter = csvLabelWriter;
        this.orderDocumentGenerator = orderDocumentGenerator;
        this.email = email;
        this.opaClient = opaClient;

        int sendAclStatus = sendAcl();
        logger.info("sent acl to open policy agent. status code [{}]", sendAclStatus);

        int sendPoliciesStatus =  sendPolicies();
        logger.info("sent policies to open policy agent. status code [{}]", sendPoliciesStatus);
    }

    @Override
    public List<Product> getAllProducts(long producerId, boolean availableOnly) throws Exception { return repository.getAllProducts(producerId, availableOnly); }

    @Override
    public Product getProductById(long id) throws Exception
    {
        return repository.getProductById(id);
    }

    @Override
    public Product updateProduct(long id, Form form, NumberFormatter numberFormatter) throws Exception
    {
        Producer producer = getProducerById(Long.parseLong(form.get(FormField.PRODUCER_ID)));
        ProductContainer container = getProductContainerById(Long.parseLong(form.get(FormField.CONTAINER_ID)));
        ProductOrigin origin = getProductOriginById(Long.parseLong(form.get(FormField.ORIGIN_ID)));

        Product product = FormConverter.convertToProduct(form,numberFormatter);
        product.setId(id);
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
    public Product addProduct(Form form, NumberFormatter numberFormatter) throws Exception
    {
        Producer producer = getProducerById(Long.parseLong(form.get(FormField.PRODUCER_ID)));
        ProductContainer container = getProductContainerById(Long.parseLong(form.get(FormField.CONTAINER_ID)));
        ProductOrigin origin = getProductOriginById(Long.parseLong(form.get(FormField.ORIGIN_ID)));

        Product product = FormConverter.convertToProduct(form,numberFormatter);
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
    public Producer updateProducer(long id, Form form) throws Exception
    {
        Producer producer = new Producer(form.get(FormField.NAME));
        producer.setId(id);
        producer.setEmailAddress(form.get(FormField.EMAIL));
        producer.setNoOrdering(Integer.parseInt(form.get(FormField.NO_ORDERING)));
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
    public Producer addProducer(Form form) throws Exception
    {
        Producer producer = new Producer(form.get(FormField.NAME));
        producer.setName(form.get(FormField.NAME));
        producer.setEmailAddress(form.get(FormField.EMAIL));
        producer.setNoOrdering(Integer.parseInt(form.get(FormField.NO_ORDERING)));
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
    public ProductContainer updateProductContainer(long id, Form form) throws Exception
    {
        ProductContainer productContainer = new ProductContainer(form.get(FormField.NAME));
        productContainer.setId(id);
        try
        {
            logger.debug("updating product container - name: [{}]", productContainer.getName());
            repository.updateProductContainer(productContainer);
        }
        catch (Exception ex)
        {

            logger.error("error adding product container: [{}]", ex.getMessage());
        }
        return productContainer;
    }

    @Override
    public ProductContainer addProductContainer(Form form) throws Exception
    {
        ProductContainer productContainer = new ProductContainer(form.get(FormField.NAME));
        productContainer.setName(form.get(FormField.NAME.NAME));
        try
        {
            logger.debug("adding product container - name: [{}]", productContainer.getName());
            repository.addProductContainer(productContainer);
        }
        catch (Exception ex)
        {
            logger.error("error adding product container: [{}]", ex.getMessage());
        }
        return productContainer;
    }

    @Override
    public boolean getIsUniqueProductContainer(long id, String name) throws Exception
    {
        return repository.getIsUniqueProductContainer(id, name);
    }

    @Override
    public void deleteProductContainer(long id) throws Exception
    {
        repository.deleteProductContainer(id);
    }

    @Override
    public void deleteProductOrder(long id) throws Exception
    {
        repository.deleteProductOrder(id);
    }

    @Override
    public void updateOrderEmailSent(ProductOrder order) throws Exception
    {
        repository.updateOrderEmailSent(order);
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
    public ProductOrigin updateProductOrigin(long id, Form form) throws Exception
    {
        ProductOrigin productOrigin = new ProductOrigin(form.get(FormField.NAME));
        productOrigin.setId(id);
        try
        {
            logger.debug("updating product origin - name: [{}]", productOrigin.getName());
            repository.updateProductOrigin(productOrigin);
        }
        catch (Exception ex)
        {

            logger.error("error adding product origin: [{}]", ex.getMessage());
        }
        return productOrigin;
    }

    @Override
    public ProductOrigin addProductOrigin(Form form) throws Exception
    {
        ProductOrigin productOrigin = new ProductOrigin(form.get(FormField.NAME));
        productOrigin.setName(form.get(FormField.NAME.NAME));
        try
        {
            logger.debug("adding product origin - name: [{}]", productOrigin.getName());
            repository.addProductOrigin(productOrigin);
        }
        catch (Exception ex)
        {
            logger.error("error adding producer: [{}]", ex.getMessage());
        }
        return productOrigin;
    }

    @Override
    public boolean getIsUniqueProductOrigin(long id, String name) throws Exception
    {
        return repository.getIsUniqueProductOrigin(id, name);
    }

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
    public User getUserById(long id) throws Exception
    {
        return repository.getUserById(id);
    }

    @Override
    public List<User> getAllUsers() throws Exception
    {
        return repository.getAllUsers();
    }

    @Override
    public void updateUser(User user) throws Exception
    {
        repository.updateUser(user);
    }

    @Override
    public OpaValidationResult validateUser(OpaInput input)
    {
        return opaClient.validateUser(input);
    }

    @Override
    public int sendAcl() throws Exception
    {
        OpaAcl acl = new OpaAcl();
        try
        {
            for (User user : getAllUsers())
            {
                acl.addUser(user.getName());
            }
        }
        catch (Exception ex)
        {
            logger.error("error retrieving users from database: [{}]", ex.getMessage());
        }

        for(Endpoints item : Endpoints.values())
        {
            String glob = item.getPath().replaceAll("\\/(:.\\w+?)\\/", "/*/");
            acl.addUrl(new OpaAclUrl(glob, item.getMethod(), item.getRole()));
        }

        int status = opaClient.sendAcl(acl);
        return status;
    }

    @Override
    public int sendPolicies() throws Exception
    {
        String rego = null;
        int status = 0;
        try
        {
            InputStream ioStream = this.getClass().getClassLoader().getResourceAsStream(Constants.REGO_FILENAME);
            rego = new String(ioStream.readAllBytes());
            status = opaClient.sendPolicies(rego);
        }
        catch (Exception ex)
        {
            logger.error("error reading policy file: [{}]. error: [{}]",Constants.REGO_FILENAME,ex.getMessage());
        }
        return status;
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

    @Override
    public byte[] getLabelsOutputFile(List<ProductLabel> productLabels) throws Exception
    {
        return csvLabelWriter.getLabelsOutputFile(productLabels);
    }

    @Override
    public byte[] getOrderDocument(Producer producer, ProductOrder order, List<Product> products) throws Exception
    {
        return orderDocumentGenerator.getOrderDocument(producer,order, products);
    }

    @Override
    public String getOrderDocumentFilename(ProductOrder order)
    {
        return orderDocumentGenerator.getOrderDocumentFilename(order);
    }

    @Override
    public long getAllProductsCount() throws Exception
    {
        return repository.getAllProductsCount();
    }

    @Override
    public Map<String,Long> getAllProducersProductsCount() throws Exception
    {
        return repository.getAllProducersProductsCount();
    }

    @Override
    public boolean sendEmail(ProductOrder order, String emailRecipient, MainConfiguration configuration)
    {
        return email.send(order, emailRecipient, configuration);
    }
}
