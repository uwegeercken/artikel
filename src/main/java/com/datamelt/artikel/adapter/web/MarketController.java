package com.datamelt.artikel.adapter.web;

import com.datamelt.artikel.app.web.ViewUtility;
import com.datamelt.artikel.app.web.util.Path;
import com.datamelt.artikel.model.Market;
import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.port.MarketApiInterface;
import com.datamelt.artikel.port.ProducerApiInterface;
import com.datamelt.artikel.service.WebService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarketController implements MarketApiInterface
{
    private static WebService service;
    private MessageBundle messages;

    public MarketController(WebService service, MessageBundle messages)
    {
        this.service = service;
        this.messages = messages;
    }

    public Route serveAllMarketsPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("messages", messages);
        model.put("pagetitle", messages.get("PAGETITLE_MARKET_LIST"));
        model.put("markets", getAllMarkets());
        return ViewUtility.render(request,model,Path.Template.MARKETS);

    };

    @Override
    public List<Market> getAllMarkets() throws Exception
    {
        return service.getAllMarkets();
    }
}
