package com.datamelt.artikel.adapter.web;

import com.datamelt.artikel.app.web.ViewUtility;
import com.datamelt.artikel.app.web.util.Path;
import com.datamelt.artikel.model.ProductOrder;
import com.datamelt.artikel.port.MessageBundleInterface;
import com.datamelt.artikel.port.ProductOrderApiInterface;
import com.datamelt.artikel.port.WebServiceInterface;
import com.datamelt.artikel.util.Constants;
import spark.Request;
import spark.Response;
import spark.Route;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductOrderController implements ProductOrderApiInterface
{
    private static WebServiceInterface service;
    private MessageBundleInterface messages;

    public ProductOrderController(WebServiceInterface service, MessageBundleInterface messages)
    {
        this.service = service;
        this.messages = messages;
    }

    public Route serveAllOrdersPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("messages", messages);
        model.put("pagetitle", messages.get("PAGETITLE_PRODUCTORDERS_LIST"));
        model.put("orders", getAllProductOrders());
        return ViewUtility.render(request,model,Path.Template.ORDERS);

    };

    @Override
    public List<ProductOrder> getAllProductOrders() throws Exception
    {
        return service.getAllProductOrders();
    }
}
