package com.datamelt.artikel.port;

import com.datamelt.artikel.adapter.web.form.ProductContainerForm;
import com.datamelt.artikel.adapter.web.form.ProductForm;
import com.datamelt.artikel.model.Market;
import com.datamelt.artikel.model.ProductContainer;

import java.util.List;

public interface ProductContainerApiInterface
{
    List<ProductContainer> getAllProductContainers() throws Exception;
    ProductContainer getProductContainerById(long id) throws Exception;
    void updateProductContainer(long id, ProductContainerForm form) throws Exception;
    void addProductContainer(ProductContainerForm form) throws Exception;
    boolean getIsUniqueProductContainer(long id, String name) throws Exception;
}
