package com.datamelt.artikel.adapter.web;

import com.datamelt.artikel.adapter.web.validator.ValidatorResult;
import com.datamelt.artikel.app.web.ViewUtility;
import com.datamelt.artikel.app.web.WebApplication;
import com.datamelt.artikel.app.web.util.Path;
import com.datamelt.artikel.config.MainConfiguration;
import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.Product;
import com.datamelt.artikel.model.ProductOrder;
import com.datamelt.artikel.port.ProductOrderApiInterface;
import com.datamelt.artikel.port.WebServiceInterface;
import com.datamelt.artikel.util.Constants;

import com.datamelt.artikel.util.FileUtility;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class ProductOrderController implements ProductOrderApiInterface
{
    private static WebServiceInterface service;
    private MainConfiguration configuration;

    public ProductOrderController(WebServiceInterface service, MainConfiguration configuration)
    {
        this.service = service;
        this.configuration = configuration;
    }

    public Route serveAllOrdersPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put(Constants.MODEL_ORDERS_KEY, getAllProductOrders());
        return ViewUtility.render(request,model,Path.Template.ORDERS);

    };

    public Route serveOrderItemsPage = (Request request, Response response) -> {

        Optional<ProductOrder> order = Optional.ofNullable(getProductOrderById(Long.parseLong(request.params(":id"))));
        Map<String, Object> model = new HashMap<>();
        if(order.isPresent())
        {
            model.put(Constants.MODEL_ORDERID_KEY,order.get().getId());
            model.put(Constants.MODEL_PRODUCTORDERITEMS_KEY, order.get().getOrderItems());
        }
        return ViewUtility.render(request,model,Path.Template.ORDERITEMS);
    };

    public Route generateOrderPdf = (Request request, Response response) -> {

        Optional<ProductOrder> order = Optional.ofNullable(getProductOrderById(Long.parseLong(request.params(":id"))));
        Map<String, Object> model = new HashMap<>();
        if(order.isPresent())
        {
            Producer producer = getProducerById(order.get().getProducerId());
            String pdfFilename = getOrderDocumentFilename(order.get());
            File pdfFile = new File(pdfFilename);
            byte[] pdfFileBytes;
            if(pdfFile.exists())
            {
                FileInputStream inputStream = new FileInputStream(pdfFile);
                pdfFileBytes = inputStream.readAllBytes();
                inputStream.close();
            }
            else
            {
                List<Product> products = getAllProducts(producer.getId());
                pdfFileBytes = getOrderDocument(producer, order.get(), products);
            }

            response.type(Constants.FILE_CONTENT_TYPE_PDF);
            response.header(Constants.CONTENT_TYPE_KEY,Constants.CONTENT_TYPE_VALUE);
            response.header(Constants.CONTENT_DISPOSITION_KEY,Constants.CONTENT_DISPOSITION_VALUE + pdfFilename);
            response.raw().getOutputStream().write(pdfFileBytes);
            response.raw().getOutputStream().flush();
            response.raw().getOutputStream().close();
        }

        return response.raw();
    };

    public Route generateOrderEmail = (Request request, Response response) -> {

        Optional<ProductOrder> order = Optional.ofNullable(getProductOrderById(Long.parseLong(request.params(":id"))));
        Map<String, Object> model = new HashMap<>();
        if(order.isPresent())
        {
            Producer producer = getProducerById(order.get().getProducerId());
            String pdfFilename = getOrderDocumentFilename(order.get());
            File pdfFile = new File(pdfFilename);
            if(!pdfFile.exists())
            {
                List<Product> products = getAllProducts(producer.getId());
                byte[] pdfFileBytes = getOrderDocument(producer, order.get(), products);
            }
            boolean success = sendEmail(order.get(), configuration);
            if(success)
            {
                model.put(Constants.MODEL_RESULT_KEY, new ValidatorResult(WebApplication.getMessages().get("INFO_EMAIL_SENT")));
            }
            else
            {
                model.put(Constants.MODEL_RESULT_KEY, new ValidatorResult(ValidatorResult.RESULTYPE_ERROR, WebApplication.getMessages().get("ERROR_EMAIL_SENT")));
            }
            model.put(Constants.MODEL_ORDERID_KEY, order.get().getId());
            model.put(Constants.MODEL_PRODUCTORDERITEMS_KEY, order.get().getOrderItems());
        }

        return ViewUtility.render(request,model,Path.Template.ORDERITEMS);
    };

    private String getOrderDocumentPdfFilename(ProductOrder order)
    {
        String pdfFilename = getOrderDocumentFilename(order);
        return FileUtility.getFullFilename(configuration.getAsciidoc().getPdfOutputFolder(), pdfFilename);
    }

    @Override
    public List<ProductOrder> getAllProductOrders() throws Exception
    {
        return service.getAllProductOrders();
    }

    @Override
    public List<Product> getAllProducts(long producerId) throws Exception
    {
        return service.getAllProducts(producerId);
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
    public byte[] getOrderDocument(Producer producer, ProductOrder order, List<Product> products) throws Exception
    {
        return service.getOrderDocument(producer, order, products);
    }

    @Override
    public String getOrderDocumentFilename(ProductOrder order)
    {
        return FileUtility.getFullFilename(configuration.getAsciidoc().getPdfOutputFolder(), service.getOrderDocumentFilename(order));
    }

    @Override
    public boolean sendEmail(ProductOrder order, MainConfiguration configuration)
    {
        return service.sendEmail(order,configuration);
    }
}
