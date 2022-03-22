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
    public static Order getOrderById(Connection connection, long id) throws Exception
    {
        PreparedStatement queryStatement = connection.prepareStatement("select * from productorder where id=?");
        queryStatement.setLong(1, id);
        ResultSet resultset = queryStatement.executeQuery();
        Order order = null;
        if(resultset.next())
        {
            order = new Order(resultset.getString("number"),resultset.getLong("timestamp"));
            order.setId(resultset.getLong("id"));

            List<Product> products = CollectionHandler.getAllOrderItems(connection, order);
            order.setItems(products);
        }
        queryStatement.clearParameters();
        resultset.close();
        queryStatement.close();
        return order;
    }

    public static Order getOrderByNumber(Connection connection, String number) throws Exception
    {
        PreparedStatement queryStatement = connection.prepareStatement("select * from productorder where number=?");
        queryStatement.setString(1, number);
        ResultSet resultset = queryStatement.executeQuery();
        Order order = null;
        if(resultset.next())
        {
            order = new Order(resultset.getString("number"),resultset.getLong("timestamp"));
            order.setId(resultset.getLong("id"));

            List<Product> products = CollectionHandler.getAllOrderItems(connection, order);
            order.setItems(products);
        }
        queryStatement.clearParameters();
        resultset.close();
        queryStatement.close();
        return order;
    }
}
