package com.datamelt.artikel.port;

import com.datamelt.artikel.adapter.web.form.ProducerForm;
import com.datamelt.artikel.adapter.web.form.ProductForm;
import com.datamelt.artikel.model.*;

import java.util.List;
import java.util.Map;

public interface WebServiceInterface
{
    List<Product> getAllProducts() throws Exception;

    Product getProductById(long id) throws Exception;

    Product updateProduct(long id, ProductForm form) throws Exception;

    Product addProduct(ProductForm form) throws Exception;

    Producer updateProducer(long id, ProducerForm form) throws Exception;

    Producer getProducerById(long id) throws Exception;

    boolean getExistProduct(String number) throws Exception;

    List<Producer> getAllProducers() throws Exception;

    List<Market> getAllMarkets() throws Exception;

    List<ProductContainer> getAllProductContainers() throws Exception;

    List<ProductOrigin> getAllProductOrigins() throws Exception;

    ProductContainer getProductContainerById(long id) throws Exception;

    ProductOrigin getProductOriginById(long id) throws Exception;

    boolean getIsUniqueProduct(long id, String number) throws Exception;

    boolean getIsUniqueProducer(long id, String name) throws Exception;

    void createDatabaseTables() throws Exception;

    void deleteProduct(long id) throws Exception;

    void deleteProducer(long id) throws Exception;

    User getUserByName(String name) throws Exception;

    Map<Long, ProductOrderItem> getShopProductOrderItems(ProductOrder order) throws Exception;

    void addProductOrder(ProductOrder order);

    List<ProductOrder> getAllProductOrders() throws Exception;
}
