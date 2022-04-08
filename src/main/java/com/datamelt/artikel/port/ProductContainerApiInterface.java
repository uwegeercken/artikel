package com.datamelt.artikel.port;

import com.datamelt.artikel.model.Market;
import com.datamelt.artikel.model.ProductContainer;

import java.util.List;

public interface ProductContainerApiInterface
{
    List<ProductContainer> getAllProductContainers() throws Exception;
}
