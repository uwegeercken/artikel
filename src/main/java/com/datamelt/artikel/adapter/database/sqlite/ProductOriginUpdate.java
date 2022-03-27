package com.datamelt.artikel.adapter.database.sqlite;

import com.datamelt.artikel.model.ProductOrigin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class ProductOriginUpdate
{
    private static final String SQL_INSERT = "insert into productorigin (name) values(?)";
    private static final String SQL_UPDATE = "update productorigin set name=? where id=?";
    private static final String SQL_DELETE = "delete from productorigin where id=?";

    private Connection connection;

    public ProductOriginUpdate(Connection connection)
    {
        this.connection = connection;
    }

    void addProductOrigin(ProductOrigin origin)
    {
        try
        {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, origin.getName());
            statement.executeUpdate();
            statement.clearParameters();

            ResultSet resultset = statement.getGeneratedKeys();
            resultset.next();
            origin.setId(resultset.getLong(1));

            resultset.close();
            statement.close();

        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    void updateProductOrigin(ProductOrigin origin)
    {
        try
        {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, origin.getName());
            statement.setLong(2, origin.getId());
            statement.executeUpdate();
            statement.clearParameters();

            statement.close();


        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    void removeProductOrigin(long id)
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
