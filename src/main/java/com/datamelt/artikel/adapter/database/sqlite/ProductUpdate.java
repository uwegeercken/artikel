package com.datamelt.artikel.adapter.database.sqlite;

import com.datamelt.artikel.model.Product;
import java.sql.*;

class ProductUpdate
{
    private static final String SQL_INSERT = "insert into product (number, name, title, subtitle, ingredients, allergenes, useforstickers, producer_id, productcontainer_id, productorigin_id, quantity,weight,price, unavailable, timestamp) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE = "update product set name=?, title=?, subtitle=?, ingredients=?, allergenes=?, useforstickers=?, number=?, producer_id=?, productcontainer_id=?, productorigin_id=?, quantity=?, weight=?, price=?, unavailable=?, timestamp=? where id=?";
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
            statement.setString(5, product.getIngredients());
            statement.setString(6, product.getAllergenes());
            statement.setInt(7, product.getUseForStickers());
            statement.setLong(8, product.getProducer().getId());
            statement.setLong(9, product.getContainer().getId());
            statement.setLong(10, product.getOrigin().getId());
            statement.setLong(11, product.getQuantity());
            statement.setDouble(12, product.getWeight());
            statement.setDouble(13, product.getPrice());
            statement.setInt(14,product.getUnavailable());
            statement.setLong(15, product.getTimestamp());

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
        statement.setString(4, product.getIngredients());
        statement.setString(5, product.getAllergenes());
        statement.setInt(6, product.getUseForStickers());
        statement.setString(7, product.getNumber());
        statement.setLong(8, product.getProducer().getId());
        statement.setLong(9, product.getContainer().getId());
        statement.setLong(10, product.getOrigin().getId());
        statement.setLong(11, product.getQuantity());
        statement.setDouble(12, product.getWeight());
        statement.setDouble(13, product.getPrice());
        statement.setInt(14,product.getUnavailable());
        statement.setLong(15, product.getTimestamp());
        statement.setLong(16, product.getId());
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
