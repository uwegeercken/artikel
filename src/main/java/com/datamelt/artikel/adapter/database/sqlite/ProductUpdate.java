package com.datamelt.artikel.adapter.database.sqlite;

import com.datamelt.artikel.model.Product;
import java.sql.*;

class ProductUpdate
{
    private static final String SQL_INSERT = "insert into product (number,name,description,producer_id, productcontainer_id, productorigin_id, quantity,weight,price) values(?,?,?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE = "update product set name=?, description=?, number=?, producer_id=?, productcontainer_id=?, productorigin_id=?, quantity=?, weight=?, price=? where id=?";
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
            statement.setString(3, product.getDescription());
            statement.setLong(4, product.getProducer().getId());
            statement.setLong(5, product.getContainer().getId());
            statement.setLong(6, product.getOrigin().getId());
            statement.setLong(7, product.getQuantity());
            statement.setDouble(8, product.getWeight());
            statement.setDouble(9, product.getPrice());

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

    void updateProduct(Product product)
    {
        try
        {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setString(3, product.getNumber());
            statement.setLong(4, product.getProducer().getId());
            statement.setLong(5, product.getContainer().getId());
            statement.setLong(6, product.getOrigin().getId());
            statement.setLong(7, product.getQuantity());
            statement.setDouble(8, product.getWeight());
            statement.setDouble(9, product.getPrice());
            statement.setLong(10, product.getId());
            statement.executeUpdate();
            statement.clearParameters();

            statement.close();

        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    void removeProduct(long id)
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
