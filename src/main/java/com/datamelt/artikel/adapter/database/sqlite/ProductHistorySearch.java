package com.datamelt.artikel.adapter.database.sqlite;

import com.datamelt.artikel.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class ProductHistorySearch
{
    private static final String SQL_QUERY_BY_ID = "select * from product_history where id=?";
    private static final String SQL_QUERY_BY_PRODUCT_ID = "select * from product_history where product_id=? and DATE(timestamp/1000, 'unixepoch')=?";
    private static final String SQL_QUERY_EXISTS = "select count(1) as counter from product_history where product_id=? and DATE(timestamp/1000, 'unixepoch')=?";

    static ProductHistory getProductHistoryById(Connection connection, long id) throws Exception
    {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_BY_ID);
        statement.setLong(1, id);
        ResultSet resultset = statement.executeQuery();
        ProductHistory productHistory = null;
        if(resultset.next())
        {
            productHistory = new ProductHistory(resultset.getLong("product_id"));
            productHistory.setId(id);
            productHistory.setProductId(resultset.getLong("product_id"));
            productHistory.setPrice(resultset.getDouble("price"));
            productHistory.setTimestamp(resultset.getLong("timestamp"));
        }
        resultset.close();
        statement.close();
        return productHistory;
    }

    static ProductHistory getProductHistoryByProductId(Connection connection, long productId, String date) throws Exception
    {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_BY_PRODUCT_ID);
        statement.setLong(1, productId);
        statement.setString(2, date);
        ResultSet resultset = statement.executeQuery();
        ProductHistory productHistory = null;
        if(resultset.next())
        {
            productHistory = new ProductHistory(productId);
            productHistory.setId(resultset.getLong("id"));
            productHistory.setPrice(resultset.getDouble("price"));
            productHistory.setTimestamp(resultset.getLong("timestamp"));
        }
        statement.clearParameters();
        resultset.close();
        statement.close();
        return productHistory;
    }

    static boolean getExistProductHistory(Connection connection, long productId, String date) throws Exception
    {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_EXISTS);
        statement.setLong(1, productId);
        statement.setString(2, date);
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
