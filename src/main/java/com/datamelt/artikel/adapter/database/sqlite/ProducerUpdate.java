package com.datamelt.artikel.adapter.database.sqlite;

import com.datamelt.artikel.model.Producer;

import java.sql.*;

class ProducerUpdate
{
    private static final String SQL_INSERT = "insert into producer (name) values(?)";
    private static final String SQL_UPDATE = "update producer set name=? where id=?";
    private static final String SQL_DELETE = "delete from producer where id=?";

    private Connection connection;

    public ProducerUpdate(Connection connection)
    {
        this.connection = connection;
    }
    public void addProducer(Producer producer)
    {
        try
        {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, producer.getName());
            statement.executeUpdate();

            ResultSet resultset = statement.getGeneratedKeys();
            resultset.next();
            producer.setId(resultset.getLong(1));

            resultset.close();
            statement.close();

        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    public void updateProducer(Producer producer)
    {
        try
        {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, producer.getName());
            statement.setLong(2, producer.getId());
            statement.executeUpdate();
            statement.clearParameters();

            statement.close();

        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    public void removeProducer(long id)
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
