package com.datamelt.artikel.adapter.database.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class ProductOrderItemUpdate
{
    private static final String SQL_INSERT = "insert into productorder_item (productorder_id,product_id, amount) values(?,?,?)";
    private static final String SQL_DELETE = "delete from productorder_item where product_id=?";
    private static final String SQL_DELETE_ALL = "delete from productorder_item where productorder_id=?";

    private Connection connection;

    public ProductOrderItemUpdate(Connection connection)
    {
        this.connection = connection;
    }

    void addOrderItem(long orderId, long productId, int amount)
    {
        try
        {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setLong(1, orderId);
            statement.setLong(2, productId);
            statement.setLong(3, amount);
            statement.executeUpdate();

        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    void removeOrderItem(long id)
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

    void removeAllOrderItems(long orderId)
    {
        try
        {
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ALL);
            statement.setLong(1, orderId);
            statement.executeUpdate();
            statement.clearParameters();

            statement.close();

        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }
}
