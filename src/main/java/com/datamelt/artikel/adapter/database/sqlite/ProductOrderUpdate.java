package com.datamelt.artikel.adapter.database.sqlite;

import com.datamelt.artikel.model.ProductOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

class ProductOrderUpdate
{
    private static final String SQL_INSERT = "insert into productorder (number, producer_id, timestamp_order_date, timestamp_created_date) values(?,?,?,?)";
    private static final String SQL_UPDATE = "update productorder set number=?, producer_id=?, timestamp_order_date=?, timestamp_created_date=? where id=?";
    private static final String SQL_DELETE = "delete from productorder where id=?";

    private static final String PRODUCT_ORDER_NUMBER_DATE_FORMAT = "yyyyMMddhhmmss";

    private Connection connection;
    private SimpleDateFormat sdf = new SimpleDateFormat(PRODUCT_ORDER_NUMBER_DATE_FORMAT);

    public ProductOrderUpdate(Connection connection)
    {
        this.connection = connection;
    }

    void addOrder(ProductOrder order)
    {
        try
        {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, getGeneratedNumber(order.getProducerId()));
            statement.setLong(2, order.getProducerId());
            statement.setLong(3, 0);
            statement.setLong(4, new Date().getTime());
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
            statement.setLong(2, order.getProducerId());
            statement.setLong(3, order.getTimestampOrderDate());
            statement.setLong(4, order.getTimestampCreatedDate());
            statement.setLong(5, order.getId());
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

    private String getGeneratedNumber(long producerId)
    {
        return producerId + "--" + sdf.format(new Date());
    }
}
