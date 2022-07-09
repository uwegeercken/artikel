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
import com.datamelt.artikel.model.*;
import com.datamelt.artikel.port.ProductApiInterface;
import com.datamelt.artikel.port.WebServiceInterface;
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
    private WebServiceInterface service;

    public ProductController(WebServiceInterface service)
    {
        this.service = service;
    }

    public Route serveAllProductsPage = (Request request, Response response) -> {
        long producerId = Long.parseLong(request.params(":producerid"));
        Producer producer = getProducerById(producerId);
        return ViewUtility.render(request,shopProductsLabelModel(producer),Path.Template.PRODUCTS);
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

        ProductOrderCollection orderCollection = request.session().attribute("ordercollection");
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
            return ViewUtility.render(request,model,Path.Template.SHOPPRODUCTS_NO_ORDERING);
        }
        else
        {
            return ViewUtility.render(request, model, Path.Template.SHOPPRODUCTS);
        }
    };

    public Route shopProduct = (Request request, Response response) -> {
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
            logger.error("error parsing amount from value [{}]. productId [{}], producerId ", request.queryParams("productamount"), productId, producerId);
        }
        ProductOrderCollection orderCollection = request.session().attribute("ordercollection");
        Optional<ProductOrder> order = Optional.ofNullable(orderCollection.get(producerId));
        if(!order.isPresent())
        {
            ProductOrderItem item = new ProductOrderItem();
            item.setProduct(getProductById(productId));
            item.setAmount(value);
            ProductOrder newOrder = new ProductOrder(producerId);
            newOrder.addOrderItem(item);
            newOrder.setProducer(producer);
            orderCollection.add(newOrder);
        }
        else
        {
            if(order.get().getOrderItems().containsKey(productId))
            {
                ProductOrderItem shopItem = order.get().getOrderItem(productId);
                shopItem.setAmount(value);
            }
            else
            {
                ProductOrderItem item = new ProductOrderItem();
                item.setProduct(getProductById(productId));
                item.setAmount(value);
                order.get().addOrderItem(item);
            }
        }
        return ViewUtility.render(request,shopProductsLabelModel(producer),Path.Template.PRODUCTS);

    };

    public Route shopProductLabels = (Request request, Response response) -> {
        long productId = Long.parseLong(request.params(":id"));
        long producerId = Long.parseLong(request.params(":producerid"));
        Producer producer = getProducerById(producerId);

        ProductOrderCollection orderCollection = request.session().attribute("ordercollection");
        Optional<ProductOrder> order = Optional.ofNullable(orderCollection.get(producerId));
        if(!order.isPresent())
        {
            ProductOrderItem item = new ProductOrderItem();
            item.setProduct(getProductById(productId));
            item.setAmount(1);
            ProductOrder newOrder = new ProductOrder(producerId, true);
            newOrder.addOrderItem(item);
            newOrder.setProducer(producer);
            orderCollection.add(newOrder);
        }
        else
        {
            if(!order.get().getOrderItems().containsKey(productId))
            {
                ProductOrderItem item = new ProductOrderItem();
                item.setProduct(getProductById(productId));
                item.setAmount(1);
                order.get().addOrderItem(item);
            }
            else
            {
                ProductOrderItem item = new ProductOrderItem();
                item.setProduct(getProductById(productId));
                order.get().removeOrderItem(item);
            }
        }
        return ViewUtility.render(request, shopProductsLabelModel(producer), Path.Template.PRODUCTS);

    };

    private Map<String, Object> shopProductsLabelModel(Producer producer)
    {
        Map<String, Object> model = new HashMap<>();
        try
        {
            model.put(Constants.MODEL_PRODUCTS_KEY, getAllProducts(producer.getId(),false));
        }
        catch (Exception ex)
        {
            logger.error("error getting products for producerId [{}]", producer.getId());
        }
        model.put(Constants.MODEL_PRODUCER_KEY, producer);
        return model;
    }

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
            logger.error("error parsing amount from value [{}]. productId [{}], producerId ", request.queryParams("productamount"), productId, producerId);
        }

        ProductOrderCollection orderCollection = request.session().attribute("ordercollection");
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

        ProductOrderCollection orderCollection = request.session().attribute("ordercollection");
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
        Producer producer = getProducerById(producerId);

        ProductOrderCollection orderCollection = request.session().attribute("ordercollection");
        ProductOrder order = orderCollection.get(producerId);

        SimpleDateFormat formatter = new SimpleDateFormat(Constants.GERMAN_DATE_ONLY_FORMAT);
        String orderDateString = request.queryParams("order_date");
        if(order!=null && orderDateString!=null && !orderDateString.trim().equals(""))
        {
            Date orderDate = formatter.parse(orderDateString);
            order.setTimestampOrderDate(orderDate.getTime());

            addProductOrder(order);
            orderCollection.remove(producerId);

            response.redirect(Path.Web.ORDERS);
            return null;
        }
        else
        {
            Map<String, Object> model = new HashMap<>();
            model.put(Constants.MODEL_RESULT_KEY, new ValidatorResult(WebApplication.getMessages().get("ERROR_NO_ORDER_DATE")));
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

        String cancelled = request.queryParams(Constants.FORM_SUBMIT);
        if(!cancelled.equals(WebApplication.getMessages().get("FORM_BUTTON_CANCEL")))
        {
            deleteProduct(Long.parseLong(request.params(":id")));
        }
        return ViewUtility.render(request, shopProductsLabelModel(producer) ,Path.Template.PRODUCTS);
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

        ProductOrderCollection orderCollection = request.session().attribute("ordercollection");
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

        Map<String, Object> model =  shopProductsLabelModel(producer);
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
            }
            else
            {
                model.put(Constants.MODEL_RESULT_KEY, result);
            }
            return ViewUtility.render(request,model,Path.Template.PRODUCT);
        }
        else
        {
            return ViewUtility.render(request, shopProductsLabelModel(producer), Path.Template.PRODUCTS);
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
    public List<Product> getAllProducts(long producerId, boolean availableOnly) throws Exception
    {
        return service.getAllProducts(producerId, availableOnly);
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
    public List<ProductOrigin> getAllProductOrigins() throws Exception
    {
        return service.getAllProductOrigins();
    }

    @Override
    public byte[] getLabelsOutputFile(long producerId) throws Exception
    {
        List<Product> products = getAllProducts(producerId, false);
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
