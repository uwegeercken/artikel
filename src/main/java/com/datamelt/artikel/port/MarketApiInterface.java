package com.datamelt.artikel.port;

import com.datamelt.artikel.model.Market;
import com.datamelt.artikel.model.Producer;

import java.util.List;

public interface MarketApiInterface
{
    List<Market> getAllMarkets() throws Exception;
}
