package com.datamelt.artikel.adapter.web;

import com.datamelt.artikel.adapter.web.form.Form;
import com.datamelt.artikel.adapter.web.form.FormConverter;
import com.datamelt.artikel.adapter.web.form.FormField;
import com.datamelt.artikel.adapter.web.form.FormValidator;
import com.datamelt.artikel.adapter.web.validator.ValidatorResult;
import com.datamelt.artikel.app.web.ViewUtility;
import com.datamelt.artikel.app.web.WebApplication;
import com.datamelt.artikel.app.web.util.NumberFormatter;
import com.datamelt.artikel.app.web.util.Path;
import com.datamelt.artikel.config.MainConfiguration;
import com.datamelt.artikel.model.*;
import com.datamelt.artikel.model.highcharts.Category;
import com.datamelt.artikel.model.highcharts.CategoryCollection;
import com.datamelt.artikel.model.highcharts.Serie;
import com.datamelt.artikel.model.highcharts.SeriesCollection;
import com.datamelt.artikel.port.ProductApiInterface;
import com.datamelt.artikel.port.WebServiceInterface;
import com.datamelt.artikel.util.CalendarUtility;
import com.datamelt.artikel.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import java.text.SimpleDateFormat;
import java.util.*;

public class ProductController implements ProductApiInterface
{
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final WebServiceInterface service;
    private MainConfiguration configuration;

    public ProductController(WebServiceInterface service, MainConfiguration configuration)
    {
        this.service = service;
        this.configuration = configuration;
    }

    public Route serveAllProductsPage = (Request request, Response response) -> {
        long producerId = Long.parseLong(request.params(":producerid"));
        Producer producer = getProducerById(producerId);
        Map<String, Object> model = new HashMap<>();
        model.put(Constants.MODEL_PRODUCER_KEY, producer);
        model.put(Constants.MODEL_UNCHANGED_PRODUCTS_FILTER_KEY, WebApplication.getMessages().get("UNCHANGED_PRODUCTS_FILTER_ALL"));
        try
        {
            model.put(Constants.MODEL_PRODUCTS_KEY, getAllProducts(producer.getId(),false, configuration.getWebApp().getAllProductsNumberOfDaysMin(), configuration.getWebApp().getAllProductsNumberOfDaysMax()));
        }
        catch (Exception ex)
        {
            logger.error("error getting products for producerId [{}]", producer.getId());
        }
        request.session().attribute(Constants.SESSION_ATTRIBUTE_PRODUCTS_CHANGED_NUMBER_OF_DAYS_MIN, configuration.getWebApp().getAllProductsNumberOfDaysMin());
        request.session().attribute(Constants.SESSION_ATTRIBUTE_PRODUCTS_CHANGED_NUMBER_OF_DAYS_MAX, configuration.getWebApp().getAllProductsNumberOfDaysMax());
        request.session().attribute(Constants.SESSION_ATTRIBUTE_FILTER, model.get(Constants.MODEL_UNCHANGED_PRODUCTS_FILTER_KEY));
        return ViewUtility.render(request, model, Path.Template.PRODUCTS);
    };

    public Route serveProductsChangedRecentlyPage = (Request request, Response response) -> {
        long producerId = Long.parseLong(request.params(":producerid"));
        Producer producer = getProducerById(producerId);
        Map<String, Object> model = new HashMap<>();
        model.put(Constants.MODEL_PRODUCER_KEY, producer);
        model.put(Constants.MODEL_UNCHANGED_PRODUCTS_FILTER_KEY, configuration.getWebApp().getRecentlyUnchangedProductsNumberOfDaysMin() + "-" + configuration.getWebApp().getRecentlyUnchangedProductsNumberOfDaysMax() + " " + WebApplication.getMessages().get("IMAGE_UNCHANGED_PRODUCTS"));
        try
        {
            model.put(Constants.MODEL_PRODUCTS_KEY, getAllProducts(producer.getId(),false, configuration.getWebApp().getRecentlyUnchangedProductsNumberOfDaysMin(), configuration.getWebApp().getRecentlyUnchangedProductsNumberOfDaysMax()));
        }
        catch (Exception ex)
        {
            logger.error("error getting products for producerId [{}]", producer.getId());
        }
        request.session().attribute(Constants.SESSION_ATTRIBUTE_PRODUCTS_CHANGED_NUMBER_OF_DAYS_MIN, configuration.getWebApp().getRecentlyUnchangedProductsNumberOfDaysMin());
        request.session().attribute(Constants.SESSION_ATTRIBUTE_PRODUCTS_CHANGED_NUMBER_OF_DAYS_MAX, configuration.getWebApp().getRecentlyUnchangedProductsNumberOfDaysMax());
        request.session().attribute(Constants.SESSION_ATTRIBUTE_FILTER, model.get(Constants.MODEL_UNCHANGED_PRODUCTS_FILTER_KEY));
        return ViewUtility.render(request, model, Path.Template.PRODUCTS);
    };

    public Route serveProductsUnchangedShortTermPage = (Request request, Response response) -> {
        long producerId = Long.parseLong(request.params(":producerid"));
        Producer producer = getProducerById(producerId);
        Map<String, Object> model = new HashMap<>();
        model.put(Constants.MODEL_PRODUCER_KEY, producer);
        model.put(Constants.MODEL_UNCHANGED_PRODUCTS_FILTER_KEY, configuration.getWebApp().getShorttermUnchangedProductsNumberOfDaysMin() + "-" + configuration.getWebApp().getShorttermUnchangedProductsNumberOfDaysMax() + " " + WebApplication.getMessages().get("IMAGE_UNCHANGED_PRODUCTS"));
        try
        {
            model.put(Constants.MODEL_PRODUCTS_KEY, getAllProducts(producer.getId(),false, configuration.getWebApp().getShorttermUnchangedProductsNumberOfDaysMin(), configuration.getWebApp().getShorttermUnchangedProductsNumberOfDaysMax()));
        }
        catch (Exception ex)
        {
            logger.error("error getting products for producerId [{}]", producer.getId());
        }
        request.session().attribute(Constants.SESSION_ATTRIBUTE_PRODUCTS_CHANGED_NUMBER_OF_DAYS_MIN, configuration.getWebApp().getShorttermUnchangedProductsNumberOfDaysMin());
        request.session().attribute(Constants.SESSION_ATTRIBUTE_PRODUCTS_CHANGED_NUMBER_OF_DAYS_MAX, configuration.getWebApp().getShorttermUnchangedProductsNumberOfDaysMax());
        request.session().attribute(Constants.SESSION_ATTRIBUTE_FILTER, model.get(Constants.MODEL_UNCHANGED_PRODUCTS_FILTER_KEY));
        return ViewUtility.render(request, model, Path.Template.PRODUCTS);
    };

    public Route serveProductsUnchangedLongTermPage = (Request request, Response response) -> {
        long producerId = Long.parseLong(request.params(":producerid"));
        Producer producer = getProducerById(producerId);
        Map<String, Object> model = new HashMap<>();
        model.put(Constants.MODEL_PRODUCER_KEY, producer);
        model.put(Constants.MODEL_UNCHANGED_PRODUCTS_FILTER_KEY,  configuration.getWebApp().getLongtermUnchangedProductsNumberOfDaysMin() + " " + WebApplication.getMessages().get("IMAGE_UNCHANGED_PRODUCTS_LONGTERM"));
        try
        {
            model.put(Constants.MODEL_PRODUCTS_KEY, getAllProducts(producer.getId(),false, configuration.getWebApp().getLongtermUnchangedProductsNumberOfDaysMin(), configuration.getWebApp().getAllProductsNumberOfDaysMax()));
        }
        catch (Exception ex)
        {
            logger.error("error getting products for producerId [{}]", producer.getId());
        }
        request.session().attribute(Constants.SESSION_ATTRIBUTE_PRODUCTS_CHANGED_NUMBER_OF_DAYS_MIN, configuration.getWebApp().getLongtermUnchangedProductsNumberOfDaysMin());
        request.session().attribute(Constants.SESSION_ATTRIBUTE_PRODUCTS_CHANGED_NUMBER_OF_DAYS_MAX, configuration.getWebApp().getAllProductsNumberOfDaysMax());
        request.session().attribute(Constants.SESSION_ATTRIBUTE_FILTER, model.get(Constants.MODEL_UNCHANGED_PRODUCTS_FILTER_KEY));
        return ViewUtility.render(request, model, Path.Template.PRODUCTS);
    };

    public Route servePriceChartPage = (Request request, Response response) -> {

        Map<String, Object> model = new HashMap<>();
        CategoryCollection categories = new CategoryCollection();
        categories.add(CalendarUtility.getWeeks(configuration.getWebApp().getChartingNumberOfWeeksToDisplay()));
        model.put("categories", categories.getValues());

        long producerId = Long.parseLong(request.params(":producerid"));
        Producer producer = getProducerById(producerId);
        model.put(Constants.MODEL_PRODUCER_KEY, producer);

        Set<String> parameters = request.queryParams();
        List<Long> ids = new ArrayList<>();
        for(String parameter : parameters)
        {
            if(parameter.startsWith("productcheck-"))
            {
                String[] parts = parameter.split("-");
                ids.add(Long.parseLong(parts[1]));
            }
        }

        SeriesCollection series = new SeriesCollection();
        for(long id : ids)
        {
            double latestValue=-1;
            Product product = getProductById(id);
            Serie serie = new Serie(product.getName());
            List<ProductHistory> productHistory = getProductHistory(product);
            HashMap<String, ProductHistory> map = new HashMap<>();
            for(int i=0; i<productHistory.size();i++)
            {
                ProductHistory history = productHistory.get(i);
                String yearWeek = history.getTimestampYearWeek();
                if(categories.contains(yearWeek))
                {
                    map.put(yearWeek, history);
                }
                else
                {
                    latestValue = history.getPrice();
                }
            }

            for(Category category : categories.getCategories())
            {
                String yearWeek = category.getValue();
                ProductHistory history = map.get(yearWeek);
                if(history!=null)
                {
                    serie.add(history.getPrice());
                    latestValue = history.getPrice();
                }
                else
                {
                    if(latestValue!=-1)
                    {
                        serie.add(latestValue);
                    }
                    else
                    {
                        serie.add(0);
                    }
                }
            }
            series.add(serie);
        }
        model.put("series", series);
        return ViewUtility.render(request, model, Path.Template.PRICECHART);
    };

    public Route serveProductPage = (Request request, Response response) -> {

        long producerId = Long.parseLong(request.params(":producerid"));
        Producer producer = getProducerById(producerId);

        Map<String, Object> model = new HashMap<>();
        model.put(Constants.MODEL_FIELDS_KEY, FormField.class);
        model.put(Constants.MODEL_PRODUCER_KEY, producer);
        Optional<Product> product = Optional.ofNullable(getProductById(Long.parseLong(request.params(":id"))));
        if(product.isPresent())
        {
            model.put(Constants.MODEL_FORM_KEY, FormConverter.convertToForm(product.get(), WebApplication.getNumberFormatter()));
        }
        else
        {
            Form form = new Form();
            form.put(FormField.ID,"0");
            form.put(FormField.QUANTITY,"1");
            form.put(FormField.PRICE,"0");
            form.put(FormField.WEIGHT,"0");
            form.put(FormField.PRODUCER_ID,String.valueOf(producerId));
            model.put(Constants.MODEL_FORM_KEY, form);
        }
        model.put(Constants.MODEL_PRODUCERS_KEY, getAllProducers());
        model.put(Constants.MODEL_CONTAINERS_KEY, getAllProductContainers());
        model.put(Constants.MODEL_ORIGINS_KEY, getAllProductOrigins());
        return ViewUtility.render(request,model,Path.Template.PRODUCT);
    };

    public Route serveShopProductsPage = (Request request, Response response) -> {
        long producerId = Long.parseLong(request.params(":producerid"));
        Producer producer = getProducerById(producerId);

        ProductOrderCollection orderCollection = request.session().attribute(Constants.SESSION_ATTRIBUTE_ORDER_COLLECTION);
        Optional<ProductOrder> order = Optional.ofNullable(orderCollection.get(producerId));
        Map<String, Object> model = new HashMap<>();
        if(order.isPresent())
        {
            model.put(Constants.MODEL_PRODUCTORDERITEMS_KEY, getShopProductOrderItems(order.get()));
            model.put(Constants.MODEL_SHOPLABELSONLY_KEY, order.get().getShopLabelsOnly());
        }
        model.put(Constants.MODEL_PRODUCER_KEY, producer);
        if(producer.getNoOrdering()==1)
        {
            return ViewUtility.render(request, model,Path.Template.SHOPPRODUCTS_NO_ORDERING);
        }
        else
        {
            return ViewUtility.render(request, model, Path.Template.SHOPPRODUCTS);
        }
    };

    public Route shopProduct = (Request request, Response response) -> {
        long producerId = Long.parseLong(request.params(":producerid"));
        Producer producer = getProducerById(producerId);

        Set<String> parameters = request.queryParams();
        Map<Long, Integer> ids = new HashMap<>();
        for(String parameter : parameters)
        {
            if(parameter.startsWith("productamount-"))
            {
                String value = request.queryParamsValues(parameter)[0];
                if(value!=null && !value.equals(""))
                {
                    int integerValue;
                    String[] parts = parameter.split("-");
                    try
                    {
                        integerValue = Integer.parseInt(value);
                    }
                    catch (Exception ex)
                    {
                        integerValue = 0;
                    }
                    ids.put(Long.parseLong(parts[1]), integerValue);
                }
            }
        }

        ProductOrderCollection orderCollection = request.session().attribute(Constants.SESSION_ATTRIBUTE_ORDER_COLLECTION);
        ProductOrder order = orderCollection.get(producerId);
        if(order==null)
        {
            order = new ProductOrder(producerId);
            order.setProducer(producer);
        }

        for(Map.Entry<Long,Integer> id : ids.entrySet())
        {
            ProductOrderItem item = new ProductOrderItem();
            item.setProduct(getProductById(id.getKey()));
            if(id.getValue() > 0)
            {
                item.setAmount(id.getValue());
                order.addOrderItem(item);
            }
            else
            {
                order.removeOrderItem(item);
            }
        }
        orderCollection.add(order);

        Map<String, Object> model = new HashMap<>();
        model.put(Constants.MODEL_PRODUCTORDERITEMS_KEY, getShopProductOrderItems(order));
        model.put(Constants.MODEL_SHOPLABELSONLY_KEY, order.getShopLabelsOnly());
        model.put(Constants.MODEL_PRODUCER_KEY, producer);
        if(producer.getNoOrdering()==1)
        {
            return ViewUtility.render(request,model,Path.Template.SHOPPRODUCTS_NO_ORDERING);
        }
        else
        {
            return ViewUtility.render(request, model, Path.Template.SHOPPRODUCTS);
        }

        //return ViewUtility.render(request,shopProductsLabelModel(producer),Path.Template.PRODUCTS);

    };

    public Route shopProductLabels = (Request request, Response response) -> {
        long producerId = Long.parseLong(request.params(":producerid"));
        Producer producer = getProducerById(producerId);

        Set<String> parameters = request.queryParams();
        List<Long> ids = new ArrayList<>();
        for(String parameter : parameters)
        {
            if(parameter.startsWith("productcheck-"))
            {
                String[] parts = parameter.split("-");
                ids.add(Long.parseLong(parts[1]));
            }
        }

        ProductOrderCollection orderCollection = request.session().attribute(Constants.SESSION_ATTRIBUTE_ORDER_COLLECTION);
        ProductOrder order = orderCollection.get(producerId);
        if(order==null)
        {
            order = new ProductOrder(producerId, true);
        }
        order.setProducer(producer);
        for(long id : ids)
        {
            ProductOrderItem item = new ProductOrderItem();
            item.setProduct(getProductById(id));
            item.setAmount(1);
            order.addOrderItem(item);
            order.setProducer(producer);
        }
        //orderCollection.add(order);

        byte[] pdfOutputFile = getLabelsOutputFile(producerId, order);
        if(pdfOutputFile!=null)
        {
            orderCollection.remove(producer.getId());

            String fullFilename = Constants.LABELS_FILE_CONTENT_DISPOSITION_VALUE_FILENAME_PART1 + "_" + producer.getName() + Constants.LABELS_FILE_CONTENT_DISPOSITION_VALUE_FILENAME_PART2;
            response.type(Constants.FILE_CONTENT_TYPE_PDF);
            response.header(Constants.CONTENT_DISPOSITION_KEY, Constants.CONTENT_DISPOSITION_VALUE + fullFilename);
            response.raw().setContentLength(pdfOutputFile.length);
            response.raw().getOutputStream().write(pdfOutputFile);
            response.raw().getOutputStream().flush();
            response.raw().getOutputStream().close();
            return null;
        }
        else
        {
            Map<String, Object> model = new HashMap<>();
            model.put(Constants.MODEL_RESULT_KEY, new ValidatorResult(ValidatorResult.RESULTYPE_ERROR, WebApplication.getMessages().get("ERROR_CREATING_LABELS")));
            model.put(Constants.MODEL_PRODUCTORDERITEMS_KEY, getShopProductOrderItems(order));
            model.put(Constants.MODEL_SHOPLABELSONLY_KEY, order.getShopLabelsOnly());
            model.put(Constants.MODEL_PRODUCER_KEY, producer);
            return ViewUtility.render(request,model,Path.Template.SHOPPRODUCTS);
        }
    };

    public Route shopProductAmount = (Request request, Response response) -> {

        long productId = Long.parseLong(request.params(":id"));
        long producerId = Long.parseLong(request.params(":producerid"));
        Producer producer = getProducerById(producerId);

        int value=0;
        try
        {
            value = Integer.parseInt(request.queryParams("productamount"));
        }
        catch(Exception ex)
        {
            logger.error("error parsing amount from value [{}]. productId [{}], producerId [{}]", request.queryParams("productamount"), productId, producerId);
        }

        ProductOrderCollection orderCollection = request.session().attribute(Constants.SESSION_ATTRIBUTE_ORDER_COLLECTION);
        ProductOrder order = orderCollection.get(producerId);
        ProductOrderItem shopItem = order.getOrderItem(productId);
        shopItem.setAmount(value);

        Map<String, Object> model = new HashMap<>();
        model.put(Constants.MODEL_PRODUCTORDERITEMS_KEY, getShopProductOrderItems(order));
        model.put(Constants.MODEL_PRODUCER_KEY, producer);
        return ViewUtility.render(request,model,Path.Template.SHOPPRODUCTS);

    };

    public Route shopProductRemove = (Request request, Response response) -> {
        long productId = Long.parseLong(request.params(":id"));
        long producerId = Long.parseLong(request.params(":producerid"));
        Producer producer = getProducerById(producerId);

        ProductOrderCollection orderCollection = request.session().attribute(Constants.SESSION_ATTRIBUTE_ORDER_COLLECTION);
        ProductOrder  order = orderCollection.get(producerId);

        ProductOrderItem shopItem = order.getOrderItem(productId);
        order.removeOrderItem(shopItem);

        Map<String, Object> model = new HashMap<>();
        model.put(Constants.MODEL_PRODUCTORDERITEMS_KEY, getShopProductOrderItems(order));
        model.put(Constants.MODEL_PRODUCER_KEY, producer);
        return ViewUtility.render(request,model,Path.Template.SHOPPRODUCTS);
    };

    public Route shopProductComplete = (Request request, Response response) -> {
        long producerId = Long.parseLong(request.params(":producerid"));
        Map<String, Object> model = new HashMap<>();

        ProductOrderCollection orderCollection = request.session().attribute(Constants.SESSION_ATTRIBUTE_ORDER_COLLECTION);
        ProductOrder order = orderCollection.get(producerId);

        SimpleDateFormat formatter = new SimpleDateFormat(Constants.GERMAN_DATE_ONLY_FORMAT);
        String orderDateString = request.queryParams("order_date");
        if(order!=null && orderDateString!=null && !orderDateString.trim().equals(""))
        {
            Date orderDate = formatter.parse(orderDateString);
            order.setTimestampOrderDate(orderDate.getTime());

            addProductOrder(order);
            orderCollection.remove(producerId);


            model.put(Constants.MODEL_ORDERS_KEY, getAllProductOrders());
            return ViewUtility.render(request,model,Path.Template.ORDERS);
        }
        else
        {
            response.redirect("/shopproducts/producer/" + producerId + "/");
            return null;
        }
    };

    public Route serveDeleteProductPage = (Request request, Response response) -> {
        long producerId = Long.parseLong(request.params(":producerid"));
        Producer producer = getProducerById(producerId);

        Map<String, Object> model = new HashMap<>();
        Product product = getProductById(Long.parseLong(request.params(":id")));
        model.put(Constants.MODEL_PRODUCT_KEY, product);
        model.put(Constants.MODEL_PRODUCER_KEY, producer);
        return ViewUtility.render(request,model,Path.Template.PRODUCT_DELETE);
    };

    public Route deleteProduct = (Request request, Response response) -> {
        long producerId = Long.parseLong(request.params(":producerid"));
        Producer producer = getProducerById(producerId);
        Map<String, Object> model = new HashMap<>();
        model.put(Constants.MODEL_PRODUCER_KEY, producer);

        int productsNumberOfDaysMin = configuration.getWebApp().getAllProductsNumberOfDaysMin();
        if(request.session().attribute(Constants.SESSION_ATTRIBUTE_PRODUCTS_CHANGED_NUMBER_OF_DAYS_MIN)!=null)
        {
            productsNumberOfDaysMin = request.session().attribute(Constants.SESSION_ATTRIBUTE_PRODUCTS_CHANGED_NUMBER_OF_DAYS_MIN);
        }
        int productsNumberOfDaysMax = configuration.getWebApp().getAllProductsNumberOfDaysMax();
        if(request.session().attribute(Constants.SESSION_ATTRIBUTE_PRODUCTS_CHANGED_NUMBER_OF_DAYS_MAX)!=null)
        {
            productsNumberOfDaysMax = request.session().attribute(Constants.SESSION_ATTRIBUTE_PRODUCTS_CHANGED_NUMBER_OF_DAYS_MAX);
        }
        if(request.session().attribute(Constants.SESSION_ATTRIBUTE_FILTER)!=null)
        {
            model.put(Constants.MODEL_UNCHANGED_PRODUCTS_FILTER_KEY,request.session().attribute(Constants.SESSION_ATTRIBUTE_FILTER));
        }
        try
        {
            model.put(Constants.MODEL_PRODUCTS_KEY, getAllProducts(producer.getId(),false, productsNumberOfDaysMin, productsNumberOfDaysMax));
        }
        catch (Exception ex)
        {
            logger.error("error getting products for producerId [{}]", producer.getId());
        }
        String cancelled = request.queryParams(Constants.FORM_SUBMIT);
        if(!cancelled.equals(WebApplication.getMessages().get("FORM_BUTTON_CANCEL")))
        {
            deleteProduct(Long.parseLong(request.params(":id")));
        }
        return ViewUtility.render(request, model ,Path.Template.PRODUCTS);
    };

    public Route createLabels = (Request request, Response response) -> {
        long producerId = Long.parseLong(request.params(":producerid"));
        Producer producer = getProducerById(producerId);
        byte[] pdfOutputFile = getLabelsOutputFile(producerId);
        String fullFilename = Constants.LABELS_FILE_CONTENT_DISPOSITION_VALUE_FILENAME_PART1 + "_" + producer.getName() + Constants.LABELS_FILE_CONTENT_DISPOSITION_VALUE_FILENAME_PART2;

        response.type(Constants.FILE_CONTENT_TYPE_PDF);
        response.header(Constants.CONTENT_DISPOSITION_KEY,Constants.CONTENT_DISPOSITION_VALUE + fullFilename);
        response.header(Constants.CONTENT_TYPE_KEY,Constants.CONTENT_TYPE_VALUE);
        response.raw().setContentLength(pdfOutputFile.length);
        response.raw().getOutputStream().write(pdfOutputFile);
        response.raw().getOutputStream().flush();
        response.raw().getOutputStream().close();
        return response.raw();
    };


    public Route createShopLabels = (Request request, Response response) -> {
        long producerId = Long.parseLong(request.params(":producerid"));
        Producer producer = getProducerById(producerId);
        Map<String, Object> model = new HashMap<>();

        ProductOrderCollection orderCollection = request.session().attribute(Constants.SESSION_ATTRIBUTE_ORDER_COLLECTION);
        ProductOrder order = orderCollection.get(producerId);
        byte[] pdfOutputFile = getLabelsOutputFile(producerId, order);
        if(pdfOutputFile!=null)
        {
            orderCollection.remove(producerId);
            String fullFilename = Constants.LABELS_FILE_CONTENT_DISPOSITION_VALUE_FILENAME_PART1 + "_" + producer.getName() + Constants.LABELS_FILE_CONTENT_DISPOSITION_VALUE_FILENAME_PART2;
            response.type(Constants.FILE_CONTENT_TYPE_PDF);
            response.header(Constants.CONTENT_DISPOSITION_KEY, Constants.CONTENT_DISPOSITION_VALUE + fullFilename);
            response.raw().setContentLength(pdfOutputFile.length);
            response.raw().getOutputStream().write(pdfOutputFile);
            response.raw().getOutputStream().flush();
            response.raw().getOutputStream().close();
            return null;
        }
        else
        {
            model.put(Constants.MODEL_RESULT_KEY, new ValidatorResult(ValidatorResult.RESULTYPE_ERROR, WebApplication.getMessages().get("ERROR_CREATING_LABELS")));
            model.put(Constants.MODEL_PRODUCTORDERITEMS_KEY, getShopProductOrderItems(order));
            model.put(Constants.MODEL_SHOPLABELSONLY_KEY, order.getShopLabelsOnly());
            model.put(Constants.MODEL_PRODUCER_KEY, producer);
            return ViewUtility.render(request,model,Path.Template.SHOPPRODUCTS);
        }
    };


    public Route serveUpdateProductPage = (Request request, Response response) -> {
        long producerId = Long.parseLong(request.params(":producerid"));
        Producer producer = getProducerById(producerId);
        Map<String, Object> model = new HashMap<>();
        model.put(Constants.MODEL_PRODUCER_KEY, producer);

        int productsNumberOfDaysMin = configuration.getWebApp().getAllProductsNumberOfDaysMin();
        if(request.session().attribute(Constants.SESSION_ATTRIBUTE_PRODUCTS_CHANGED_NUMBER_OF_DAYS_MIN)!=null)
        {
            productsNumberOfDaysMin = request.session().attribute(Constants.SESSION_ATTRIBUTE_PRODUCTS_CHANGED_NUMBER_OF_DAYS_MIN);
        }
        int productsNumberOfDaysMax = configuration.getWebApp().getAllProductsNumberOfDaysMax();
        if(request.session().attribute(Constants.SESSION_ATTRIBUTE_PRODUCTS_CHANGED_NUMBER_OF_DAYS_MAX)!=null)
        {
            productsNumberOfDaysMax = request.session().attribute(Constants.SESSION_ATTRIBUTE_PRODUCTS_CHANGED_NUMBER_OF_DAYS_MAX);
        }
        if(request.session().attribute(Constants.SESSION_ATTRIBUTE_FILTER)!=null)
        {
            model.put(Constants.MODEL_UNCHANGED_PRODUCTS_FILTER_KEY, request.session().attribute(Constants.SESSION_ATTRIBUTE_FILTER));
        }
        try
        {
            model.put(Constants.MODEL_PRODUCTS_KEY, getAllProducts(producer.getId(),false, productsNumberOfDaysMin, productsNumberOfDaysMax));
        }
        catch (Exception ex)
        {
            logger.error("error getting products for producerId [{}]", producer.getId());
        }

        String cancelled = request.queryParams(Constants.FORM_SUBMIT);
        if(!cancelled.equals(WebApplication.getMessages().get("FORM_BUTTON_CANCEL")))
        {
            Form form = Form.createFormFromQueryParameters(request);
            model.put(Constants.MODEL_FORM_KEY, form);
            model.put(Constants.MODEL_FIELDS_KEY, FormField.class);
            model.put(Constants.MODEL_PRODUCERS_KEY, getAllProducers());
            model.put(Constants.MODEL_CONTAINERS_KEY, getAllProductContainers());
            model.put(Constants.MODEL_ORIGINS_KEY, getAllProductOrigins());

            boolean isUniqueProduct = getIsUniqueProduct(Long.parseLong(form.get(FormField.ID)),form.get(FormField.NUMBER));
            ValidatorResult result = FormValidator.validate(form, WebApplication.getMessages(), isUniqueProduct, WebApplication.getNumberFormatter());
            if(result.getResultType() == ValidatorResult.RESULT_TYPE_OK)
            {
                addOrUpdateProduct(model, form);
                if(producer.getNoOrdering()==1)
                {
                    ProductOrderCollection orderCollection = request.session().attribute(Constants.SESSION_ATTRIBUTE_ORDER_COLLECTION);
                    ProductOrder order = orderCollection.get(producerId);
                    if (order == null)
                    {
                        order = new ProductOrder(producerId, true);
                        orderCollection.add(order);
                    }
                    ProductOrderItem item = new ProductOrderItem();
                    item.setProduct(getProductById(Long.parseLong(form.get(FormField.ID))));
                    item.setAmount(1);
                    order.addOrderItem(item);
                    order.setProducer(producer);
                }
                return ViewUtility.render(request, model, Path.Template.PRODUCTS);
            }
            else
            {
                model.put(Constants.MODEL_RESULT_KEY, result);
                return ViewUtility.render(request,model,Path.Template.PRODUCT);
            }

            //return ViewUtility.render(request,model,Path.Template.PRODUCT);
        }
        else
        {
            return ViewUtility.render(request, model, Path.Template.PRODUCTS);
        }
    };

    private void addOrUpdateProduct(Map<String, Object> model, Form form)
    {
        if (Long.parseLong(form.get(FormField.ID)) > 0)
        {
            try
            {
                updateProduct(Long.parseLong(form.get(FormField.ID)), form, WebApplication.getNumberFormatter());
                model.put(Constants.MODEL_RESULT_KEY, new ValidatorResult(WebApplication.getMessages().get("PRODUCT_FORM_CHANGED")));
            }
            catch (Exception ex)
            {
                logger.error("error updating product with id [{}], error [{}]", form.get(FormField.ID), ex.getMessage());
                model.put(Constants.MODEL_RESULT_KEY, new ValidatorResult(ValidatorResult.RESULTYPE_ERROR, WebApplication.getMessages().get("PRODUCT_FORM_CHANGE_ERROR")));
            }
        }
        else
        {
            try
            {
                addProduct(form, WebApplication.getNumberFormatter());
                model.put(Constants.MODEL_RESULT_KEY, new ValidatorResult(WebApplication.getMessages().get("PRODUCT_FORM_ADDED")));
            }
            catch (Exception ex)
            {
                logger.error("error updating product, error [{}]", ex.getMessage());
                model.put(Constants.MODEL_RESULT_KEY, new ValidatorResult(ValidatorResult.RESULTYPE_ERROR, WebApplication.getMessages().get("PRODUCT_FORM_ADD_ERROR")));
            }
        }
    }

    @Override
    public List<Product> getAllProducts(long producerId, boolean availableOnly, int changedSinceNumberOfDaysMin, int changedSinceNumberOfDaysMax) throws Exception
    {
        return service.getAllProducts(producerId, availableOnly, changedSinceNumberOfDaysMin, changedSinceNumberOfDaysMax);
    }

    @Override
    public List<ProductOrder> getAllProductOrders() throws Exception
    {
        return service.getAllProductOrders();
    }

    @Override
    public Map<Long, ProductOrderItem> getShopProductOrderItems(ProductOrder order) throws Exception
    {
        return service.getShopProductOrderItems(order);
    }

    @Override
    public List<Producer> getAllProducers() throws Exception
    {
        return service.getAllProducers();
    }

    @Override
    public List<ProductContainer> getAllProductContainers() throws Exception
    {
        return service.getAllProductContainers();
    }

    @Override
    public List<ProductHistory> getProductHistory(Product product) throws Exception
    {
        return service.getProductHistory(product);
    }

    @Override
    public List<ProductOrigin> getAllProductOrigins() throws Exception
    {
        return service.getAllProductOrigins();
    }

    @Override
    public byte[] getLabelsOutputFile(long producerId) throws Exception
    {
        List<Product> products = getAllProducts(producerId, false, configuration.getWebApp().getAllProductsNumberOfDaysMin(), configuration.getWebApp().getAllProductsNumberOfDaysMax() );
        List<ProductLabel> labels = new ArrayList<>();
        for(Product product : products)
        {
            labels.add(new ProductLabel(product, WebApplication.getMessages()));
        }
        return service.getLabelsOutputFile(labels);
    }
    @Override
    public byte[] getLabelsOutputFile(long producerId, ProductOrder order) throws Exception
    {
        List<Product> products = order.getProducts();
        List<ProductLabel> labels = new ArrayList<>();
        for(Product product : products)
        {
            labels.add(new ProductLabel(product, WebApplication.getMessages()));
        }
        return service.getLabelsOutputFile(labels);
    }


    @Override
    public Product getProductById(long id) throws Exception
    {
        return service.getProductById(id);
    }

    @Override
    public void updateProduct(long id, Form form, NumberFormatter numberFormatter) throws Exception
    {
        service.updateProduct(id, form, numberFormatter);
    }

    @Override
    public void addProduct(Form form, NumberFormatter numberFormatter) throws Exception
    {
        service.addProduct(form, numberFormatter);
    }

    @Override
    public boolean getIsUniqueProduct(long id, String number) throws Exception
    {
        return service.getIsUniqueProduct(id, number);
    }

    @Override
    public void deleteProduct(long id) throws Exception
    {
        service.deleteProduct(id);
    }

    @Override
    public void addProductOrder(ProductOrder order) throws Exception
    {
        service.addProductOrder(order);

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
}
