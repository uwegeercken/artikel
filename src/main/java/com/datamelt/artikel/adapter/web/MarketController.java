package com.datamelt.artikel.adapter.web;

import com.datamelt.artikel.app.web.ViewUtility;
import com.datamelt.artikel.app.web.util.Path;
import com.datamelt.artikel.model.Market;
import com.datamelt.artikel.port.MarketApiInterface;
import com.datamelt.artikel.port.WebServiceInterface;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarketController implements MarketApiInterface
{
    private static WebServiceInterface service;

    public MarketController(WebServiceInterface service)
    {
        this.service = service;
    }

    public Route serveAllMarketsPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("markets", getAllMarkets());
        return ViewUtility.render(request,model,Path.Template.MARKETS);

    };

    @Override
    public List<Market> getAllMarkets() throws Exception
    {
        return service.getAllMarkets();
    }
}
