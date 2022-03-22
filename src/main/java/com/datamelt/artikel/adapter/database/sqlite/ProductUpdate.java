package com.datamelt.artikel.adapter.database.sqlite;

import com.datamelt.artikel.model.Product;
import java.sql.*;

class ProductUpdate
{
    private final Connection connection;

    private PreparedStatement insertStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement deleteStatement;

    public ProductUpdate(Connection connection)
    {
        this.connection = connection;
        initStatements();
    }

    private void initStatements()
    {
        try
        {
            insertStatement = connection.prepareStatement("insert into product (number,name,description,producer_id,quantity,weight,price) values(?,?,?,?,?,?,?)");
            updateStatement = connection.prepareStatement("update product set name=?,description=?,number=?, producer_id=?, quantity=?, weight=?, price=? where id=?");
            deleteStatement = connection.prepareStatement("delete from product where id=?");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void addProduct(Product product)
    {
        try
        {
            insertStatement.setString(1, product.getNumber());
            insertStatement.setString(2, product.getName());
            insertStatement.setString(3, product.getDescription());
            insertStatement.setLong(4, product.getProducer().getId());
            insertStatement.setLong(5, product.getQuantity());
            insertStatement.setDouble(6, product.getWeight());
            insertStatement.setDouble(7, product.getPrice());

            insertStatement.executeUpdate();
            insertStatement.clearParameters();

            ResultSet resultset = insertStatement.getGeneratedKeys();
            resultset.next();
            product.setId(resultset.getLong(1));

            resultset.close();
            insertStatement.close();


        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    public void updateProduct(Product product)
    {
        try
        {
            updateStatement.setString(1, product.getName());
            updateStatement.setString(2, product.getDescription());
            updateStatement.setString(3, product.getNumber());
            updateStatement.setLong(4, product.getProducer().getId());
            updateStatement.setLong(5, product.getQuantity());
            updateStatement.setDouble(6, product.getWeight());
            updateStatement.setDouble(7, product.getPrice());
            updateStatement.executeUpdate();
            updateStatement.clearParameters();

            updateStatement.close();

        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    public void removeProduct(long id)
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
