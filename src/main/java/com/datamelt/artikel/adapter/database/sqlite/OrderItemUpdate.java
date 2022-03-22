package com.datamelt.artikel.adapter.database.sqlite;

import com.datamelt.artikel.model.Order;
import com.datamelt.artikel.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

class OrderItemUpdate
{
    private Connection connection = null;

    private PreparedStatement insertStatement;
    private PreparedStatement deleteStatement;
    private PreparedStatement deleteAllItemsStatement;

    public OrderItemUpdate(Connection connection)
    {
        this.connection = connection;
        initStatements();
    }

    private void initStatements()
    {
        try
        {
            insertStatement = connection.prepareStatement("insert into productorder_item (productorder_id,product_id) values(?,?)");
            deleteStatement = connection.prepareStatement("delete from productorder_item where product_id=?");
            deleteAllItemsStatement = connection.prepareStatement("delete from productorder_item where productorder_id=?");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    public void addOrderItem(long orderId, long productId)
    {
        try
        {
            insertStatement.setLong(1, orderId);
            insertStatement.setLong(2, productId);
            insertStatement.executeUpdate();
            insertStatement.clearParameters();

            insertStatement.close();

        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    public void removeOrderItem(long id)
    {
        try
        {
            deleteStatement.setLong(1, id);
            deleteStatement.executeUpdate();
            deleteStatement.clearParameters();

            deleteStatement.close();

        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    public void removeAllOrderItems(long orderId)
    {
        try
        {
            deleteAllItemsStatement.setLong(1, orderId);
            deleteAllItemsStatement.executeUpdate();
            deleteAllItemsStatement.clearParameters();

            deleteAllItemsStatement.close();

        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }
}
