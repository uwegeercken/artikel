package com.datamelt.artikel.adapter.database.sqlite;

import com.datamelt.artikel.model.ProductContainer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class ProductContainerSearch
{
    private static final String SQL_QUERY_BY_ID = "select * from productcontainer where id=?";
    private static final String SQL_QUERY_BY_NAME = "select * from productcontainer where name=?";
    private static final String SQL_QUERY_EXISTS = "select count(1) as counter from productcontainer where name=?";
    private static final String SQL_QUERY_IS_UNIQUE = "select count(1) as counter from productcontainer where name=? and id!=?";

    static ProductContainer getProductContainerById(Connection connection, long id) throws Exception
    {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_BY_ID);
        statement.setLong(1, id);
        ResultSet resultset = statement.executeQuery();
        ProductContainer container = null;
        if(resultset.next())
        {
            container = new ProductContainer(resultset.getString("name"));
            container.setId(resultset.getLong("id"));
        }
        statement.clearParameters();
        resultset.close();
        statement.close();
        return container;
    }

    static ProductContainer getProductContainerByName(Connection connection, String name) throws Exception
    {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_BY_NAME);
        statement.setString(1, name);
        ResultSet resultset = statement.executeQuery();
        ProductContainer container = null;
        if(resultset.next())
        {
            container = new ProductContainer(resultset.getString("name"));
            container.setId(resultset.getLong("id"));
        }
        statement.clearParameters();
        resultset.close();
        statement.close();
        return container;
    }

    static boolean getExistProductContainer(Connection connection, String name) throws Exception
    {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_EXISTS);
        statement.setString(1, name);
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

    public static boolean getIsUniqueProductContainer(Connection connection, long id, String name) throws Exception
    {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_IS_UNIQUE);
        statement.setString(1, name);
        statement.setLong(2, id);
        ResultSet resultset = statement.executeQuery();
        boolean isUnique = false;
        if(resultset.next())
        {
            isUnique = resultset.getLong("counter") == 0;
        }
        statement.clearParameters();
        resultset.close();
        statement.close();
        return isUnique;
    }
}
