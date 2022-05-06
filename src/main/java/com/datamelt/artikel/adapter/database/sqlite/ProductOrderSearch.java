package com.datamelt.artikel.adapter.database.sqlite;

import com.datamelt.artikel.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class ProductOrderSearch
{
    private static final String SQL_QUERY_BY_ID = "select * from productorder where id=?";
    private static final String SQL_QUERY_BY_NUMBER = "select * from productorder where number=?";
    private static final String SQL_QUERY_EXISTS = "select count(1) as counter from productorder where number=?";

    static ProductOrder getOrderById(Connection connection, long id) throws Exception
    {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_BY_ID);
        statement.setLong(1, id);
        ResultSet resultset = statement.executeQuery();
        ProductOrder order = null;
        if(resultset.next())
        {
            order = new ProductOrder(resultset.getString("number"),resultset.getLong("timestamp"));
            order.setId(resultset.getLong("id"));

            Map<Long, ProductOrderItem> items = CollectionHandler.getAllProductOrderItems(connection, order);
            order.setOrderItems(items);
        }
        statement.clearParameters();
        resultset.close();
        statement.close();
        return order;
    }

    static ProductOrder getOrderByNumber(Connection connection, String number) throws Exception
    {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_BY_NUMBER);
        statement.setString(1, number);
        ResultSet resultset = statement.executeQuery();
        ProductOrder order = null;
        if(resultset.next())
        {
            order = new ProductOrder(resultset.getString("number"),resultset.getLong("timestamp"));
            order.setId(resultset.getLong("id"));

            Map<Long, ProductOrderItem> items = CollectionHandler.getAllProductOrderItems(connection, order);
            order.setOrderItems(items);
        }
        statement.clearParameters();
        resultset.close();
        statement.close();
        return order;
    }

    static boolean getExistOrder(Connection connection, String number) throws Exception
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
}
