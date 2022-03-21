package com.datamelt.artikel.adapter.database.sqlite;

import java.sql.*;

class ProducerUpdate
{
    private Connection connection = null;

    private PreparedStatement insertStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement deleteStatement;

    public ProducerUpdate(Connection connection)
    {
        this.connection = connection;
        initStatements();
    }

    private void initStatements()
    {
        try
        {
            insertStatement = connection.prepareStatement("insert into producer (name) values(?)");
            updateStatement = connection.prepareStatement("update product set name=? where id=?");
            deleteStatement = connection.prepareStatement("delete from product where id=?");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    public void addProducer(com.datamelt.artikel.model.Producer producer)
    {
        try
        {
            insertStatement.setString(1, producer.getName());
            insertStatement.executeUpdate();
            insertStatement.clearParameters();

            ResultSet resultset = insertStatement.getGeneratedKeys();
            resultset.next();
            producer.setId(resultset.getLong(1));

        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    public void updateProducer(com.datamelt.artikel.model.Producer producer)
    {
        try
        {
            updateStatement.setString(1, producer.getName());
            updateStatement.setLong(2, producer.getId());
            updateStatement.executeUpdate();
            updateStatement.clearParameters();

        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    public void removeProducer(long id)
    {
        try
        {
            deleteStatement.setLong(1, id);
            deleteStatement.executeUpdate();
            deleteStatement.clearParameters();

        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }
}
