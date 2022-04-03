package com.datamelt.artikel.adapter.web;

import com.datamelt.artikel.app.web.ViewUtility;
import com.datamelt.artikel.app.web.util.Path;
import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.Product;
import com.datamelt.artikel.port.ProducerApiInterface;
import com.datamelt.artikel.port.ProductApiInterface;
import com.datamelt.artikel.service.WebService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProducerController implements ProducerApiInterface
{
    private static WebService service;

    public ProducerController(WebService service)
    {
        this.service = service;
    }

    public Route serveAllProducersPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("pagetitle", "Produktliste");
        model.put("producers", getAllProducers());
        return ViewUtility.render(request,model,Path.Template.PRODUCERS);

    };

    @Override
    public List<Producer> getAllProducers() throws Exception
    {
        return service.getAllProducers();
    }
}
