package com.datamelt.artikel.adapter.database.sqlite;

import com.datamelt.artikel.model.ProductOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class ProductOrderUpdate
{
    private static final String SQL_INSERT = "insert into productorder (number,timestamp) values(?,?)";
    private static final String SQL_UPDATE = "update productorder set number=?, timestamp=? where id=?";
    private static final String SQL_DELETE = "delete from productorder where id=?";

    private Connection connection;

    public ProductOrderUpdate(Connection connection)
    {
        this.connection = connection;
    }

    void addOrder(ProductOrder order)
    {
        try
        {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, order.getNumber());
            statement.setLong(2, order.getTimestamp());
            statement.executeUpdate();

            ResultSet resultset = statement.getGeneratedKeys();
            resultset.next();
            order.setId(resultset.getLong(1));

            resultset.close();
            statement.close();

        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    void updateOrder(ProductOrder order)
    {
        try
        {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, order.getNumber());
            statement.setLong(2, order.getTimestamp());
            statement.setLong(3, order.getId());
            statement.executeUpdate();
            statement.clearParameters();

            statement.close();

        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    void removeOrder(long id)
    {
        try
        {
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
            statement.setLong(1, id);
            statement.executeUpdate();
            statement.clearParameters();

            statement.close();

        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }
}
