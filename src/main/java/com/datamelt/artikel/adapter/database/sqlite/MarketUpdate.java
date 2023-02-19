package com.datamelt.artikel.adapter.database.sqlite;

import com.datamelt.artikel.model.Market;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class MarketUpdate
{
    private static final String SQL_INSERT = "insert into market (name, type) values(?,?)";
    private static final String SQL_UPDATE = "update market set name=?, type=? where id=?";
    private static final String SQL_DELETE = "delete from market where id=?";

    private Connection connection;

    public MarketUpdate(Connection connection)
    {
        this.connection = connection;
    }

    void addMarket(Market market)
    {
        try
        {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, market.getName());
            statement.setLong(2, market.getType());
            statement.executeUpdate();
            statement.clearParameters();

            ResultSet resultset = statement.getGeneratedKeys();
            resultset.next();
            market.setId(resultset.getLong(1));

            resultset.close();
            statement.close();

        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    void updateMarket(Market market)
    {
        try
        {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, market.getName());
            statement.setLong(2, market.getType());
            statement.setLong(3, market.getId());
            statement.executeUpdate();
            statement.clearParameters();

            statement.close();


        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    void removeMarket(long id)
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
