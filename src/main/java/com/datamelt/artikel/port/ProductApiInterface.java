package com.datamelt.artikel.port;

import com.datamelt.artikel.adapter.web.form.ProductForm;
import com.datamelt.artikel.model.*;

import java.util.List;
import java.util.Map;

public interface ProductApiInterface
{
    List<Product> getAllProducts(long producerId) throws Exception;
    Product getProductById(long id) throws Exception;
    void updateProduct(long id, ProductForm form) throws Exception;
    void addProduct(ProductForm form) throws Exception;
    boolean getIsUniqueProduct(long id, String number) throws Exception;
    void deleteProduct(long id) throws Exception;
    void addProductOrder(ProductOrder order) throws Exception;
    Map<Long, ProductOrderItem> getShopProductOrderItems(ProductOrder order) throws Exception;

    List<Producer> getAllProducers() throws Exception;
    List<ProductContainer> getAllProductContainers() throws Exception;
    List<ProductOrigin> getAllProductOrigins() throws Exception;
}
