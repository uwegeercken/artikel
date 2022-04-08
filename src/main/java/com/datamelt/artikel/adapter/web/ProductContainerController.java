package com.datamelt.artikel.adapter.web;

import com.datamelt.artikel.app.web.ViewUtility;
import com.datamelt.artikel.app.web.util.Path;
import com.datamelt.artikel.model.Market;
import com.datamelt.artikel.model.ProductContainer;
import com.datamelt.artikel.port.MarketApiInterface;
import com.datamelt.artikel.port.ProductContainerApiInterface;
import com.datamelt.artikel.service.WebService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductContainerController implements ProductContainerApiInterface
{
    private static WebService service;
    private MessageBundle messages;

    public ProductContainerController(WebService service, MessageBundle messages)
    {
        this.service = service;
        this.messages = messages;
    }

    public Route serveAllProductContainersPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("messages", messages);
        model.put("pagetitle", messages.get("PAGETITLE_PRODUCTCONTAINER_LIST"));
        model.put("containers", getAllProductContainers());
        return ViewUtility.render(request,model,Path.Template.PRODUCTCONTAINERS);

    };

    @Override
    public List<ProductContainer> getAllProductContainers() throws Exception
    {
        return service.getAllProductContainers();
    }
}
