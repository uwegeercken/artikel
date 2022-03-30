package com.datamelt.artikel.port;

import com.datamelt.artikel.model.Market;
import com.datamelt.artikel.model.Order;
import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.Product;

import java.util.List;

public interface ProductApiInterface
{
    void addProduct(Product product);
    void updateProduct(Product product);
    void removeProduct(long id);
    Product getProductById(long id) throws Exception;
    Product getProductByName(String name) throws Exception;
    Product getProductByNumber(String number) throws Exception;
    List<Product> getAllProducts() throws Exception;
}
