package com.datamelt.artikel.adapter.database.sqlite;

import com.datamelt.artikel.model.Product;

import java.sql.Connection;
import java.util.List;

class ProductSearch
{
    public static Product getProductById(Connection connection, long id) throws Exception
    {
        List<Product> products = CollectionHandler.getAllProducts(connection);
        for(Product product : products)
        {
            if(product.getId()== id)
            {
                return product;
            }
        }
        return null;
    }

    public static Product getProductByName(Connection connection, String name) throws Exception
    {
        List<Product> products = CollectionHandler.getAllProducts(connection);
        for(Product product : products)
        {
            if(product.getName().equals(name))
            {
                return product;
            }
        }
        return null;
    }

    public static Product getProductByNumber(Connection connection, String number) throws Exception
    {
        List<Product> products = CollectionHandler.getAllProducts(connection);
        for(Product product : products)
        {
            if(product.getNumber().equals(number))
            {
                return product;
            }
        }
        return null;
    }
}
