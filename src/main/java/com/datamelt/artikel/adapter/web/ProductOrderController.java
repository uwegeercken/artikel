package com.datamelt.artikel.adapter.web;

import com.datamelt.artikel.adapter.web.form.Form;
import com.datamelt.artikel.adapter.web.form.FormField;
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
            String pdfFullFilename = FileUtility.getFullFilename(configuration.getAsciidoc().getPdfOutputFolder(), pdfFilename);
            File pdfFile = new File(pdfFullFilename);
            byte[] pdfFileBytes;
            if(pdfFile.exists())
            {
                FileInputStream inputStream = new FileInputStream(pdfFile);
                pdfFileBytes = inputStream.readAllBytes();
                inputStream.close();
            }
            else
            {
                List<Product> products = getAllProducts(producer.getId(), true, configuration.getWebApp().getAllProductsNumberOfDaysMin(), configuration.getWebApp().getAllProductsNumberOfDaysMax());
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

    public Route selectOrderEmailAddress = (Request request, Response response) -> {

        ProductOrder order = getProductOrderById(Long.parseLong(request.params(":id")));
        Producer producer = getProducerById(order.getProducerId());
        Map<String, Object> model = new HashMap<>();
        model.put(Constants.MODEL_FIELDS_KEY, FormField.class);

        model.put(Constants.MODEL_ORDER_KEY,order);

        int emailCounter=0;
        List<Producer> producers = getAllProducers();
        for(Producer p : getAllProducers())
        {
            if(p.getEmailAddress()!=null && !p.getEmailAddress().trim().equals(""))
            {
               emailCounter++;
            }
        }
        model.put(Constants.MODEL_TOTAL_NUMBER_OF_EMAIL_ADDRESSES, emailCounter);
        model.put(Constants.MODEL_PRODUCERS_KEY, producers);
        Form form = new Form();
        form.put(FormField.ID, String.valueOf(producer.getId()));
        form.put(FormField.EMAIL, producer.getEmailAddress());
        model.put(Constants.MODEL_FORM_KEY, form);

        return ViewUtility.render(request,model,Path.Template.SELECT_ORDER_EMAIL);

    };

    public Route generateOrderEmail = (Request request, Response response) -> {

        ProductOrder order = getProductOrderById(Long.parseLong(request.params(":id")));
        Form form = Form.createFormFromQueryParameters(request);
        Map<String, Object> model = new HashMap<>();
        String cancelled = request.queryParams(Constants.FORM_SUBMIT);
        if(!cancelled.equals(WebApplication.getMessages().get("FORM_BUTTON_CANCEL")))
        {
            Producer producer = getProducerById(order.getProducerId());
            String pdfFilename = getOrderDocumentFilename(order);
            String pdfFullFilename = FileUtility.getFullFilename(configuration.getAsciidoc().getPdfOutputFolder(), pdfFilename);
            File pdfFile = new File(pdfFullFilename);
            if(!pdfFile.exists())
            {
                List<Product> products = getAllProducts(producer.getId(), true, configuration.getWebApp().getAllProductsNumberOfDaysMin(), configuration.getWebApp().getAllProductsNumberOfDaysMax());
                byte[] pdfFileBytes = getOrderDocument(producer, order, products);
            }
            boolean success = sendEmail(order, form.get(FormField.EMAIL), configuration);
            if(success)
            {
                updateOrderEmailSent(order);
                model.put(Constants.MODEL_RESULT_KEY, new ValidatorResult(WebApplication.getMessages().get("INFO_EMAIL_SENT")));
            }
            else
            {
                model.put(Constants.MODEL_RESULT_KEY, new ValidatorResult(ValidatorResult.RESULTYPE_ERROR, WebApplication.getMessages().get("ERROR_EMAIL_SENT")));
            }
        }
        model.put(Constants.MODEL_ORDERS_KEY, getAllProductOrders());
        return ViewUtility.render(request,model,Path.Template.ORDERS);
    };

    public Route serveDeleteProductOrderPage = (Request request, Response response) -> {
        long orderId = Long.parseLong(request.params(":id"));
        ProductOrder order = getProductOrderById(orderId);
        order.setProducer(getProducerById(order.getProducerId()));

        Map<String, Object> model = new HashMap<>();
        model.put(Constants.MODEL_ORDER_KEY, order);
        return ViewUtility.render(request,model,Path.Template.ORDER_DELETE);
    };

    public Route deleteProductOrder = (Request request, Response response) -> {
        long orderId = Long.parseLong(request.params(":id"));
        ProductOrder order = getProductOrderById(orderId);
        order.setProducer(getProducerById(order.getProducerId()));

        String cancelled = request.queryParams(Constants.FORM_SUBMIT);
        if(!cancelled.equals(WebApplication.getMessages().get("FORM_BUTTON_CANCEL")))
        {
            deleteProductOrder(Long.parseLong(request.params(":id")));
        }
        Map<String, Object> model = new HashMap<>();
        model.put(Constants.MODEL_ORDERS_KEY, getAllProductOrders());
        return ViewUtility.render(request,model,Path.Template.ORDERS);
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
    public List<Product> getAllProducts(long producerId, boolean availableOnly, int changedSinceNumberOfDaysMin, int changedSinceNumberOfDaysMax) throws Exception
    {
        return service.getAllProducts(producerId, availableOnly,changedSinceNumberOfDaysMin, changedSinceNumberOfDaysMax);
    }

    @Override
    public List<Producer> getAllProducers() throws Exception
    {
        return service.getAllProducers();
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
        return service.getOrderDocumentFilename(order);
    }

    @Override
    public boolean sendEmail(ProductOrder order, String emailRecipient, MainConfiguration configuration)
    {
        return service.sendEmail(order, emailRecipient, configuration);
    }

    @Override
    public void updateOrderEmailSent(ProductOrder order) throws Exception
    {
        service.updateOrderEmailSent(order);
    }

    @Override
    public void deleteProductOrder(long id) throws Exception
    {
        service.deleteProductOrder(id);
    }
}
