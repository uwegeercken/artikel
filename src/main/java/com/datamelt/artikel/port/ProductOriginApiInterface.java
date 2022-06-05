package com.datamelt.artikel.port;

import com.datamelt.artikel.adapter.web.form.Form;
import com.datamelt.artikel.model.ProductContainer;
import com.datamelt.artikel.model.ProductOrigin;

import java.util.List;

public interface ProductOriginApiInterface
{
    List<ProductOrigin> getAllProductOrigins() throws Exception;
    ProductOrigin getProductOriginById(long id) throws Exception;
    void updateProductOrigin(long id, Form form) throws Exception;
    void addProductOrigin(Form form) throws Exception;
    boolean getIsUniqueProductOrigin(long id, String name) throws Exception;
}
