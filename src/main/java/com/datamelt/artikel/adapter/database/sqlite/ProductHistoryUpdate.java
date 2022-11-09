package com.datamelt.artikel.adapter.database.sqlite;

import com.datamelt.artikel.model.Product;
import com.datamelt.artikel.model.ProductHistory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class ProductHistoryUpdate
{
    private static final String SQL_INSERT = "insert into product_history (product_id, price, timestamp) values(?,?,?)";
    private static final String SQL_UPDATE = "update product_history set product_id=?, price=?, timestamp=? where id=?";
    private static final String SQL_DELETE = "delete from product_history where id=?";

    private final Connection connection;

    public ProductHistoryUpdate(Connection connection)
    {
        this.connection = connection;
    }

    void addProductHistory(ProductHistory productHistory)
    {
        try
        {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setLong(1, productHistory.getProductId());
            statement.setDouble(2, productHistory.getPrice());
            statement.setLong(3, productHistory.getTimestamp());

            statement.executeUpdate();
            statement.clearParameters();

            ResultSet resultset = statement.getGeneratedKeys();
            resultset.next();
            productHistory.setId(resultset.getLong(1));

            resultset.close();
            statement.close();
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    void updateProductHistory(ProductHistory productHistory) throws Exception
    {
        PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
        statement.setLong(1, productHistory.getProductId());
        statement.setDouble(2, productHistory.getPrice());
        statement.setLong(3, productHistory.getTimestamp());
        statement.setLong(4, productHistory.getId());
        statement.executeUpdate();
        statement.clearParameters();

        statement.close();
    }

    void deleteProductHistory(long id)
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
