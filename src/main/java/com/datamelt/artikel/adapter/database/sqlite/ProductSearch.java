package com.datamelt.artikel.adapter.database.sqlite;

import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

class ProductSearch
{
    public static Product getProductById(Connection connection, long id) throws Exception
    {
        PreparedStatement queryStatement = connection.prepareStatement("select * from product where id=?");
        queryStatement.setLong(1, id);
        ResultSet resultset = queryStatement.executeQuery();
        Product product = null;
        if(resultset.next())
        {
            Producer producer = ProducerSearch.getProducerById(connection, resultset.getLong("producer_id"));
            product = new Product.ProductBuilder(resultset.getString("number"), resultset.getString("name"), producer)
                    .id(resultset.getLong("id"))
                    .description(resultset.getString("description"))
                    .build();
        }
        queryStatement.clearParameters();
        resultset.close();
        queryStatement.close();
        return product;
    }

    public static Product getProductByName(Connection connection, String name) throws Exception
    {
        PreparedStatement queryStatement = connection.prepareStatement("select * from product where name=?");
        queryStatement.setString(1, name);
        ResultSet resultset = queryStatement.executeQuery();
        Product product = null;
        if(resultset.next())
        {
            Producer producer = ProducerSearch.getProducerById(connection, resultset.getLong("producer_id"));
            product = new Product.ProductBuilder(resultset.getString("number"), resultset.getString("name"), producer)
                    .id(resultset.getLong("id"))
                    .description(resultset.getString("description"))
                    .build();
        }
        queryStatement.clearParameters();
        resultset.close();
        queryStatement.close();
        return product;
    }

    public static Product getProductByNumber(Connection connection, String number) throws Exception
    {
        PreparedStatement queryStatement = connection.prepareStatement("select * from product where number=?");
        queryStatement.setString(1, number);
        ResultSet resultset = queryStatement.executeQuery();
        Product product = null;
        if(resultset.next())
        {
            Producer producer = ProducerSearch.getProducerById(connection, resultset.getLong("producer_id"));
            product = new Product.ProductBuilder(resultset.getString("number"), resultset.getString("name"), producer)
                    .id(resultset.getLong("id"))
                    .description(resultset.getString("description"))
                    .build();
        }
        queryStatement.clearParameters();
        resultset.close();
        queryStatement.close();
        return product;
    }
}
