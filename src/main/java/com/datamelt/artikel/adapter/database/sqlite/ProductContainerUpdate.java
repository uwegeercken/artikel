package com.datamelt.artikel.adapter.database.sqlite;

import com.datamelt.artikel.model.ProductContainer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class ProductContainerUpdate
{
    private static final String SQL_INSERT = "insert into productcontainer (name) values(?)";
    private static final String SQL_UPDATE = "update productcontainer set name=? where id=?";
    private static final String SQL_DELETE = "delete from productcontainer where id=?";

    private Connection connection;

    public ProductContainerUpdate(Connection connection)
    {
        this.connection = connection;
    }

    void addProductContainer(ProductContainer container)
    {
        try
        {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, container.getName());
            statement.executeUpdate();
            statement.clearParameters();

            ResultSet resultset = statement.getGeneratedKeys();
            resultset.next();
            container.setId(resultset.getLong(1));

            resultset.close();
            statement.close();

        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    void updateProductContainer(ProductContainer container)
    {
        try
        {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, container.getName());
            statement.setLong(2, container.getId());
            statement.executeUpdate();
            statement.clearParameters();

            statement.close();


        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    void removeProductContainer(long id)
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
