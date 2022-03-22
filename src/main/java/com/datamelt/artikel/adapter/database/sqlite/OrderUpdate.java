package com.datamelt.artikel.adapter.database.sqlite;

import com.datamelt.artikel.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class OrderUpdate
{
    private Connection connection = null;

    private PreparedStatement insertStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement deleteStatement;

    public OrderUpdate(Connection connection)
    {
        this.connection = connection;
        initStatements();
    }

    private void initStatements()
    {
        try
        {
            insertStatement = connection.prepareStatement("insert into productorder (number,timestamp) values(?,?)");
            updateStatement = connection.prepareStatement("update productorder set number=?, timestamp=? where id=?");
            deleteStatement = connection.prepareStatement("delete from productorder where id=?");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    public void addOrder(Order order)
    {
        try
        {
            insertStatement.setString(1, order.getNumber());
            insertStatement.setLong(2, order.getTimestamp());
            insertStatement.executeUpdate();
            insertStatement.clearParameters();

            ResultSet resultset = insertStatement.getGeneratedKeys();
            resultset.next();
            order.setId(resultset.getLong(1));

            resultset.close();
            insertStatement.close();

        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    public void updateOrder(Order order)
    {
        try
        {
            updateStatement.setString(1, order.getNumber());
            updateStatement.setLong(2, order.getTimestamp());
            updateStatement.setLong(3, order.getId());
            updateStatement.executeUpdate();
            updateStatement.clearParameters();

            updateStatement.close();

        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    public void removeOrder(long id)
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
}
