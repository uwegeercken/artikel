package com.datamelt.artikel.adapter.database.sqlite;

import com.datamelt.artikel.model.Product;
import java.sql.*;
import java.util.Date;

class ProductUpdate
{
    private static final String SQL_INSERT = "insert into product (number, name, title, subtitle, producer_id, productcontainer_id, productorigin_id, quantity,weight,price, unavailable, timestamp) values(?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE = "update product set name=?, title=?, subtitle=?, number=?, producer_id=?, productcontainer_id=?, productorigin_id=?, quantity=?, weight=?, price=?, unavailable=?, timestamp=? where id=?";
    private static final String SQL_DELETE = "delete from product where id=?";

    private final Connection connection;

    public ProductUpdate(Connection connection)
    {
        this.connection = connection;
    }

    void addProduct(Product product)
    {
        try
        {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, product.getNumber());
            statement.setString(2, product.getName());
            statement.setString(3, product.getTitle());
            statement.setString(4, product.getSubtitle());
            statement.setLong(5, product.getProducer().getId());
            statement.setLong(6, product.getContainer().getId());
            statement.setLong(7, product.getOrigin().getId());
            statement.setLong(8, product.getQuantity());
            statement.setDouble(9, product.getWeight());
            statement.setDouble(10, product.getPrice());
            statement.setInt(11,product.getUnavailable());
            statement.setLong(12, product.getTimestamp());

            statement.executeUpdate();
            statement.clearParameters();

            ResultSet resultset = statement.getGeneratedKeys();
            resultset.next();
            product.setId(resultset.getLong(1));

            resultset.close();
            statement.close();

        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    void updateProduct(Product product) throws Exception
    {
        PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
        statement.setString(1, product.getName());
        statement.setString(2, product.getTitle());
        statement.setString(3, product.getSubtitle());
        statement.setString(4, product.getNumber());
        statement.setLong(5, product.getProducer().getId());
        statement.setLong(6, product.getContainer().getId());
        statement.setLong(7, product.getOrigin().getId());
        statement.setLong(8, product.getQuantity());
        statement.setDouble(9, product.getWeight());
        statement.setDouble(10, product.getPrice());
        statement.setInt(11,product.getUnavailable());
        statement.setLong(12, product.getTimestamp());
        statement.setLong(13, product.getId());
        statement.executeUpdate();
        statement.clearParameters();

        statement.close();
    }

    void deleteProduct(long id)
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
