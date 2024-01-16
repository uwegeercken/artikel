package com.datamelt.artikel.port;

import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.Product;
import com.datamelt.artikel.model.User;

import java.util.List;

public interface IndexApiInterface
{
    long getAllProductsCount() throws Exception;
}
