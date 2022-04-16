package com.datamelt.artikel.adapter.database.sqlite;

import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.Product;
import com.datamelt.artikel.model.ProductContainer;
import com.datamelt.artikel.model.ProductOrigin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class ProductSearch
{
    private static final String SQL_QUERY_BY_ID = "select * from product where id=?";
    private static final String SQL_QUERY_BY_NAME = "select * from product where name=?";
    private static final String SQL_QUERY_BY_NUMBER = "select * from product where number=?";
    private static final String SQL_QUERY_EXISTS = "select count(1) as counter from product where number=?";
    private static final String SQL_QUERY_IS_UNIQUE = "select count(1) as counter from product where number=? and id!=?";

    static Product getProductById(Connection connection, long id) throws Exception
    {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_BY_ID);
        statement.setLong(1, id);
        ResultSet resultset = statement.executeQuery();
        Product product = null;
        if(resultset.next())
        {
            Producer producer = ProducerSearch.getProducerById(connection, resultset.getLong("producer_id"));
            ProductContainer container = ProductContainerSearch.getProductContainerById(connection, resultset.getLong("productcontainer_id"));
            ProductOrigin origin = ProductOriginSearch.getProductOriginById(connection, resultset.getLong("productorigin_id"));
            product = new Product(resultset.getString("number"));
            product.setId(id);
            product.setName(resultset.getString("name"));
            product.setDescription(resultset.getString("description"));
            product.setQuantity(resultset.getInt("quantity"));
            product.setWeight(resultset.getDouble("weight"));
            product.setPrice(resultset.getDouble("price"));
            product.setContainer(container);
            product.setProducer(producer);
            product.setOrigin(origin);
            product.setTimestamp(resultset.getLong("timestamp"));
        }
        resultset.close();
        statement.close();
        return product;
    }

    static Product getProductByName(Connection connection, String name) throws Exception
    {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_BY_NAME);
        statement.setString(1, name);
        ResultSet resultset = statement.executeQuery();
        Product product = null;
        if(resultset.next())
        {
            Producer producer = ProducerSearch.getProducerById(connection, resultset.getLong("producer_id"));
            ProductContainer container = ProductContainerSearch.getProductContainerById(connection, resultset.getLong("productcontainer_id"));
            ProductOrigin origin = ProductOriginSearch.getProductOriginById(connection, resultset.getLong("productorigin_id"));
            product = new Product(resultset.getString("number"));
            product.setId(resultset.getLong("id"));
            product.setName(name);
            product.setDescription(resultset.getString("description"));
            product.setQuantity(resultset.getInt("quantity"));
            product.setWeight(resultset.getDouble("weight"));
            product.setPrice(resultset.getDouble("price"));
            product.setContainer(container);
            product.setProducer(producer);
            product.setOrigin(origin);
            product.setTimestamp(resultset.getLong("timestamp"));
        }
        statement.clearParameters();
        resultset.close();
        statement.close();
        return product;
    }

    static Product getProductByNumber(Connection connection, String number) throws Exception
    {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_BY_NUMBER);
        statement.setString(1, number);
        ResultSet resultset = statement.executeQuery();
        Product product = null;
        if(resultset.next())
        {
            Producer producer = ProducerSearch.getProducerById(connection, resultset.getLong("producer_id"));
            ProductContainer container = ProductContainerSearch.getProductContainerById(connection, resultset.getLong("productcontainer_id"));
            ProductOrigin origin = ProductOriginSearch.getProductOriginById(connection, resultset.getLong("productorigin_id"));
            product = new Product(number);
            product.setId(resultset.getLong("id"));
            product.setName(resultset.getString("name"));
            product.setDescription(resultset.getString("description"));
            product.setQuantity(resultset.getInt("quantity"));
            product.setWeight(resultset.getDouble("weight"));
            product.setPrice(resultset.getDouble("price"));
            product.setContainer(container);
            product.setProducer(producer);
            product.setOrigin(origin);
        }
        statement.clearParameters();
        resultset.close();
        statement.close();
        return product;
    }

    static boolean getExistProduct(Connection connection, String number) throws Exception
    {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_EXISTS);
        statement.setString(1, number);
        ResultSet resultset = statement.executeQuery();
        boolean exist = false;
        if(resultset.next())
        {
            exist = resultset.getLong("counter") == 1;
        }
        statement.clearParameters();
        resultset.close();
        statement.close();
        return exist;
    }

    public static boolean getIsUniqueProduct(Connection connection, long id, String number) throws Exception
    {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_IS_UNIQUE);
        statement.setString(1, number);
        statement.setLong(2, id);
        ResultSet resultset = statement.executeQuery();
        boolean isUnique = false;
        if(resultset.next())
        {
            isUnique = resultset.getLong("counter") == 1;
        }
        statement.clearParameters();
        resultset.close();
        statement.close();
        return isUnique;
    }
}
