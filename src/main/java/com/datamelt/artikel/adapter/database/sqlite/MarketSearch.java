package com.datamelt.artikel.adapter.database.sqlite;

import com.datamelt.artikel.model.Market;

import java.sql.Connection;
import java.util.List;

class MarketSearch
{
    public static Market getMarketById(Connection connection, long id) throws Exception
    {
        List<Market> markets = CollectionHandler.getAllMarkets(connection);
        for(Market market : markets)
        {
            if(market.getId()== id)
            {
                return market;
            }
        }
        return null;
    }

    public static Market getMarketByName(Connection connection, String name) throws Exception
    {
        List<Market> markets = CollectionHandler.getAllMarkets(connection);
        for(Market market : markets)
        {
            if(market.getName().equals(name))
            {
                return market;
            }
        }
        return null;
    }
}
