package com.datamelt.mogkdata.adapter.database.sqlite;

import com.datamelt.mogkdata.model.Market;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class MarketUpdate
{
    private Connection connection = null;

    private PreparedStatement insertStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement deleteStatement;

    public MarketUpdate(Connection connection)
    {
        this.connection = connection;
        initStatements();
    }

    private void initStatements()
    {
        try
        {
            insertStatement = connection.prepareStatement("insert into market (name) values(?)");
            updateStatement = connection.prepareStatement("update market set name=? where id=?");
            deleteStatement = connection.prepareStatement("delete from market where id=?");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    public void addMarket(Market market)
    {
        try
        {
            insertStatement.setString(1, market.getName());
            insertStatement.executeUpdate();
            insertStatement.clearParameters();

            ResultSet resultset = insertStatement.getGeneratedKeys();
            resultset.next();
            market.setId(resultset.getLong(1));

        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    public void updateMarket(Market market)
    {
        try
        {
            updateStatement.setString(1, market.getName());
            updateStatement.setLong(2, market.getId());
            updateStatement.executeUpdate();
            updateStatement.clearParameters();

        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    public void removeMarket(long id)
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
