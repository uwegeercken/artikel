package com.datamelt.artikel.port;

import com.datamelt.artikel.adapter.web.form.Form;
import com.datamelt.artikel.model.ProductContainer;

import java.util.List;

public interface ProductContainerApiInterface
{
    List<ProductContainer> getAllProductContainers() throws Exception;
    ProductContainer getProductContainerById(long id) throws Exception;
    void updateProductContainer(long id, Form form) throws Exception;
    void addProductContainer(Form form) throws Exception;
    boolean getIsUniqueProductContainer(long id, String name) throws Exception;
    void deleteProductContainer(long id) throws Exception;
}
