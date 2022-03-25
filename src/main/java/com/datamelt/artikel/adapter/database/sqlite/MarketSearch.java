package com.datamelt.artikel.adapter.database.sqlite;

import com.datamelt.artikel.model.Market;
import com.datamelt.artikel.model.Producer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

class MarketSearch
{
    private static final String SQL_QUERY_BY_ID = "select * from market where id=?";
    private static final String SQL_QUERY_BY_NAME = "select * from market where name=?";
    private static final String SQL_QUERY_EXISTS = "select count(1) as counter from market where name=?";

    public static Market getMarketById(Connection connection, long id) throws Exception
    {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_BY_ID);
        statement.setLong(1, id);
        ResultSet resultset = statement.executeQuery();
        Market market = null;
        if(resultset.next())
        {
            market = new Market(resultset.getString("name"));
            market.setId(resultset.getLong("id"));
        }
        statement.clearParameters();
        resultset.close();
        statement.close();
        return market;
    }

    public static Market getMarketByName(Connection connection, String name) throws Exception
    {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_BY_NAME);
        statement.setString(1, name);
        ResultSet resultset = statement.executeQuery();
        Market market = null;
        if(resultset.next())
        {
            market = new Market(resultset.getString("name"));
            market.setId(resultset.getLong("id"));
        }
        statement.clearParameters();
        resultset.close();
        statement.close();
        return market;
    }

    public static boolean getExistMarket(Connection connection, String name) throws Exception
{
    PreparedStatement statement = connection.prepareStatement(SQL_QUERY_EXISTS);
    statement.setString(1, name);
    ResultSet resultset = statement.executeQuery();
    boolean exist = false;
    if(resultset.next())
    {
        exist = resultset.getLong("counter") == 1;
    }
    statement.clearParameters();
    resultset.close();
    statement.close();
    return exist;
}
}
