package com.datamelt.artikel.port;

import com.datamelt.artikel.adapter.web.form.ProductForm;
import com.datamelt.artikel.model.*;

import java.util.List;

public interface ProductApiInterface
{
    List<Product> getAllProducts() throws Exception;
    Product getProductById(long id) throws Exception;
    void updateProduct(long id, ProductForm form) throws Exception;
    void addProduct(ProductForm form) throws Exception;
    boolean getIsUniqueProduct(long id, String number) throws Exception;
    void deleteProduct(long id) throws Exception;

    List<Producer> getAllProducers() throws Exception;
    List<ProductContainer> getAllProductContainers() throws Exception;
    List<ProductOrigin> getAllProductOrigins() throws Exception;
}
