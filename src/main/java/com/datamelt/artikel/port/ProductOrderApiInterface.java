package com.datamelt.artikel.port;

import com.datamelt.artikel.config.MainConfiguration;
import com.datamelt.artikel.model.Market;
import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.Product;
import com.datamelt.artikel.model.ProductOrder;
import org.apache.logging.log4j.core.config.Order;

import java.util.List;

public interface ProductOrderApiInterface
{
    List<ProductOrder> getAllProductOrders() throws Exception;
    List<Product> getAllProducts(long producerId) throws Exception;
    List<Producer> getAllProducers() throws Exception;
    ProductOrder getProductOrderById(long id) throws Exception;
    Producer getProducerById(long producerId) throws Exception;
    byte [] getOrderDocument(Producer producer, ProductOrder order, List<Product> products) throws Exception;
    String getOrderDocumentFilename(ProductOrder order);
    boolean sendEmail(ProductOrder order, String emailRecipient, MainConfiguration configuration);
    void updateOrderEmailSent(ProductOrder order) throws Exception;
    void deleteProductOrder(long id) throws Exception;

}
