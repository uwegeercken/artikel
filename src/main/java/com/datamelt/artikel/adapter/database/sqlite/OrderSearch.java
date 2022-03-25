package com.datamelt.artikel.adapter.database.sqlite;

import com.datamelt.artikel.model.Market;
import com.datamelt.artikel.model.Order;
import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

class OrderSearch
{
    private static final String SQL_QUERY_BY_ID = "select * from productorder where id=?";
    private static final String SQL_QUERY_BY_NUMBER = "select * from productorder where number=?";
    private static final String SQL_QUERY_EXISTS = "select count(1) as counter from productorder where number=?";

    static Order getOrderById(Connection connection, long id) throws Exception
    {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_BY_ID);
        statement.setLong(1, id);
        ResultSet resultset = statement.executeQuery();
        Order order = null;
        if(resultset.next())
        {
            order = new Order(resultset.getString("number"),resultset.getLong("timestamp"));
            order.setId(resultset.getLong("id"));

            List<Product> products = CollectionHandler.getAllOrderItems(connection, order);
            order.setItems(products);
        }
        statement.clearParameters();
        resultset.close();
        statement.close();
        return order;
    }

    static Order getOrderByNumber(Connection connection, String number) throws Exception
    {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_BY_NUMBER);
        statement.setString(1, number);
        ResultSet resultset = statement.executeQuery();
        Order order = null;
        if(resultset.next())
        {
            order = new Order(resultset.getString("number"),resultset.getLong("timestamp"));
            order.setId(resultset.getLong("id"));

            List<Product> products = CollectionHandler.getAllOrderItems(connection, order);
            order.setItems(products);
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
