package com.datamelt.artikel.adapter.database.sqlite;

import com.datamelt.artikel.model.Market;
import com.datamelt.artikel.model.Producer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

class MarketSearch
{
    public static Market getMarketById(Connection connection, long id) throws Exception
    {
        PreparedStatement queryStatement = connection.prepareStatement("select * from market where id=?");
        queryStatement.setLong(1, id);
        ResultSet resultset = queryStatement.executeQuery();
        Market market = null;
        if(resultset.next())
        {
            market = new Market(resultset.getString("name"));
            market.setId(resultset.getLong("id"));
        }
        queryStatement.clearParameters();
        resultset.close();
        queryStatement.close();
        return market;
    }

    public static Market getMarketByName(Connection connection, String name) throws Exception
    {
        PreparedStatement queryStatement = connection.prepareStatement("select * from market where name=?");
        queryStatement.setString(1, name);
        ResultSet resultset = queryStatement.executeQuery();
        Market market = null;
        if(resultset.next())
        {
            market = new Market(resultset.getString("name"));
            market.setId(resultset.getLong("id"));
        }
        queryStatement.clearParameters();
        resultset.close();
        queryStatement.close();
        return market;
    }
}
