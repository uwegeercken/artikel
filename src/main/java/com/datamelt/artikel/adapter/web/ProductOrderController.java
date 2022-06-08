package com.datamelt.artikel.adapter.web;

import com.datamelt.artikel.app.web.ViewUtility;
import com.datamelt.artikel.app.web.util.NumberFormatter;
import com.datamelt.artikel.app.web.util.Path;
import com.datamelt.artikel.config.AsciidocConfiguration;
import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.ProductOrder;
import com.datamelt.artikel.port.MessageBundleInterface;
import com.datamelt.artikel.port.ProductOrderApiInterface;
import com.datamelt.artikel.port.WebServiceInterface;
import com.datamelt.artikel.util.Constants;

import spark.Request;
import spark.Response;
import spark.Route;

import java.util.*;

public class ProductOrderController implements ProductOrderApiInterface
{
    private static WebServiceInterface service;
    private MessageBundleInterface messages;
    private AsciidocConfiguration configuration;
    private NumberFormatter numberFormatter;

    public ProductOrderController(WebServiceInterface service, MessageBundleInterface messages, NumberFormatter numberFormatter, AsciidocConfiguration configuration)
    {
        this.service = service;
        this.messages = messages;
        this.configuration = configuration;
        this.numberFormatter = numberFormatter;
    }

    public Route serveAllOrdersPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("messages", messages);
        model.put("pagetitle", messages.get("PAGETITLE_PRODUCTORDERS_LIST"));
        model.put("orders", getAllProductOrders());
        model.put("numberFormatter", numberFormatter);
        return ViewUtility.render(request,model,Path.Template.ORDERS);

    };

    public Route serveOrderItemsPage = (Request request, Response response) -> {

        Optional<ProductOrder> order = Optional.ofNullable(getProductOrderById(Long.parseLong(request.params(":id"))));
        Map<String, Object> model = new HashMap<>();
        if(order.isPresent())
        {
            model.put("productorderitems", order.get().getOrderItems());
        }
        model.put("messages", messages);
        model.put("numberFormatter", numberFormatter);
        model.put("pagetitle", messages.get("PAGETITLE_PRODUCTORDER_ITEMS"));
        return ViewUtility.render(request,model,Path.Template.ORDERITEMS);
    };

    public Route generateOrderPdf = (Request request, Response response) -> {

        Optional<ProductOrder> order = Optional.ofNullable(getProductOrderById(Long.parseLong(request.params(":id"))));
        Map<String, Object> model = new HashMap<>();

        if(order.isPresent())
        {
            Producer producer = getProducerById(order.get().getProducerId());
            byte[] pdfOutputFile = getOrderDocument(producer, order.get());
            String pdfFilename = getOrderDocumentFilename(producer, order.get());

            response.type(Constants.ORDER_FILE_CONTENT_TYPE);
            response.header(Constants.LABELS_FILE_CONTENT_DISPOSITION_KEY,Constants.LABELS_FILE_CONTENT_DISPOSITION_VALUE + pdfFilename);
            response.raw().getOutputStream().write(pdfOutputFile);
            response.raw().getOutputStream().flush();
            response.raw().getOutputStream().close();
        }

        return null;
    };

    @Override
    public List<ProductOrder> getAllProductOrders() throws Exception
    {
        return service.getAllProductOrders();
    }

    @Override
    public ProductOrder getProductOrderById(long id) throws Exception
    {
        return service.getProductOrderById(id);
    }

    @Override
    public Producer getProducerById(long producerId) throws Exception
    {
        return service.getProducerById(producerId);
    }

    @Override
    public byte[] getOrderDocument(Producer producer, ProductOrder order) throws Exception
    {
        return service.getOrderDocument(producer, order);
    }

    @Override
    public String getOrderDocumentFilename(Producer producer, ProductOrder order)
    {
        return service.getOrderDocumentFilename(producer, order);
    }
}
