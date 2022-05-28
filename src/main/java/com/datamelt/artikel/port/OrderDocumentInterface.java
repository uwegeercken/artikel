package com.datamelt.artikel.port;

import com.datamelt.artikel.model.*;

public interface OrderDocumentInterface
{
    byte [] getOrderDocument(Producer producer, ProductOrder order);
}
