package com.datamelt.artikel.port;

import com.datamelt.artikel.model.*;

import java.util.List;

public interface OrderDocumentInterface
{
    byte [] getOrderDocument(Producer producer, ProductOrder order, List<Product> products);
    String getOrderDocumentFilename(Producer producer, ProductOrder order);
}
