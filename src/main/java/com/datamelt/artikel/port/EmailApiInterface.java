package com.datamelt.artikel.port;

import com.datamelt.artikel.config.MainConfiguration;
import com.datamelt.artikel.model.ProductOrder;

public interface EmailApiInterface
{
    boolean send(ProductOrder order, MainConfiguration configuration);
}
