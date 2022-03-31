package com.datamelt.artikel.port;

import com.datamelt.artikel.model.Market;
import com.datamelt.artikel.model.Order;
import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.Product;

import java.util.List;

public interface ProductApiInterface
{
    List<Product> getAllProducts() throws Exception;
}
