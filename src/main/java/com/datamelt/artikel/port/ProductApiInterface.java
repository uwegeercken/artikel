package com.datamelt.artikel.port;

import com.datamelt.artikel.model.*;

import java.util.List;
import java.util.Map;

public interface ProductApiInterface
{
    List<Product> getAllProducts() throws Exception;
    Product getProductById(long id) throws Exception;
    Product updateProduct(long id, Map<String,String> queryParamsMap) throws Exception;
    Product addProduct(Map<String,String> queryParamsMap) throws Exception;

    List<Producer> getAllProducers() throws Exception;
    List<ProductContainer> getAllProductContainers() throws Exception;
    List<ProductOrigin> getAllProductOrigins() throws Exception;
}
