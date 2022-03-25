package com.datamelt.artikel.adapter.database.sqlite;

import com.datamelt.artikel.model.Market;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class OrderItemSearch
{
    private static final String SQL_QUERY_EXISTS = "select count(1) as counter from productorder_item where productorder_id=? and product_id=?";

    public static boolean getExistOrderItem(Connection connection, long orderId, long productId) throws Exception
    {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_EXISTS);
        statement.setLong(1, orderId);
        statement.setLong(2, productId);
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
