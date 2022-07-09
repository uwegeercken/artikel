package com.datamelt.artikel.port;

import com.datamelt.artikel.adapter.web.form.Form;
import com.datamelt.artikel.app.web.util.NumberFormatter;
import com.datamelt.artikel.model.*;

import java.util.List;
import java.util.Map;

public interface ProductApiInterface
{
    List<Product> getAllProducts(long producerId, boolean availableOnly) throws Exception;
    Product getProductById(long id) throws Exception;
    void updateProduct(long id, Form form, NumberFormatter numberFormatter) throws Exception;
    void addProduct(Form form, NumberFormatter numberFormatter) throws Exception;
    boolean getIsUniqueProduct(long id, String number) throws Exception;
    void deleteProduct(long id) throws Exception;
    void addProductOrder(ProductOrder order) throws Exception;
    Map<Long, ProductOrderItem> getShopProductOrderItems(ProductOrder order) throws Exception;

    List<Producer> getAllProducers() throws Exception;
    List<ProductContainer> getAllProductContainers() throws Exception;
    List<ProductOrigin> getAllProductOrigins() throws Exception;
    byte[] getLabelsOutputFile(long producerId) throws Exception;
    byte[] getLabelsOutputFile(long producerId, ProductOrder order) throws Exception;

    Producer getProducerById(long producerId) throws Exception;

    byte[] getOrderDocument(Producer producer, ProductOrder order, List<Product> products) throws Exception;
    String getOrderDocumentFilename(ProductOrder order);
}
