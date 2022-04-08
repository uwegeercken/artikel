package com.datamelt.artikel.port;

import com.datamelt.artikel.model.ProductContainer;
import com.datamelt.artikel.model.ProductOrigin;

import java.util.List;

public interface ProductOriginApiInterface
{
    List<ProductOrigin> getAllProductOrigins() throws Exception;
}
