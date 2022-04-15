package com.datamelt.artikel.adapter.web;

import com.datamelt.artikel.app.web.ViewUtility;
import com.datamelt.artikel.app.web.util.Path;
import com.datamelt.artikel.model.ProductContainer;
import com.datamelt.artikel.model.ProductOrigin;
import com.datamelt.artikel.port.ProductContainerApiInterface;
import com.datamelt.artikel.port.ProductOriginApiInterface;
import com.datamelt.artikel.service.WebService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductOriginController implements ProductOriginApiInterface
{
    private static WebService service;
    private MessageBundle messages;

    public ProductOriginController(WebService service, MessageBundle messages)
    {
        this.service = service;
        this.messages = messages;
    }

    public Route serveAllProductOriginsPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("messages", messages);
        model.put("pagetitle", messages.get("PAGETITLE_PRODUCTORIGIN_LIST"));
        model.put("origins", getAllProductOrigins());
        return ViewUtility.render(request,model,Path.Template.PRODUCTORIGINS);

    };

    @Override
    public List<ProductOrigin> getAllProductOrigins() throws Exception
    {
        return service.getAllProductOrigins();
    }
}