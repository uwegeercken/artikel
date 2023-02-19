package com.datamelt.artikel.port;

import com.datamelt.artikel.adapter.web.form.Form;
import com.datamelt.artikel.model.Market;
import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.ProductContainer;

import java.util.List;

public interface MarketApiInterface
{
    List<Market> getAllMarkets() throws Exception;
    Market getMarketById(long id) throws Exception;
    void updateMarket(long id, Form form) throws Exception;
    void addMarket(Form form) throws Exception;
    boolean getIsUniqueMarket(long id, String name) throws Exception;
    void deleteMarket(long id) throws Exception;
}
