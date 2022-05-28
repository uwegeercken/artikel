package com.datamelt.artikel.port;

import com.datamelt.artikel.model.Market;
import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.ProductOrder;
import org.apache.logging.log4j.core.config.Order;

import java.util.List;

public interface ProductOrderApiInterface
{
    List<ProductOrder> getAllProductOrders() throws Exception;
    ProductOrder getProductOrderById(long id) throws Exception;
    Producer getProducerById(long producerId) throws Exception;
    byte [] getOrderDocument(Producer producer, ProductOrder order) throws Exception;
    String getOrderDocumentFilename(Producer producer, ProductOrder order);
}
