package com.datamelt.artikel.adapter.web;

import com.datamelt.artikel.adapter.web.form.Form;
import com.datamelt.artikel.adapter.web.form.FormConverter;
import com.datamelt.artikel.adapter.web.form.FormField;
import com.datamelt.artikel.adapter.web.form.FormValidator;
import com.datamelt.artikel.adapter.web.validator.ValidatorResult;
import com.datamelt.artikel.app.web.ViewUtility;
import com.datamelt.artikel.app.web.util.NumberFormatter;
import com.datamelt.artikel.app.web.util.Path;
import com.datamelt.artikel.model.*;
import com.datamelt.artikel.port.MessageBundleInterface;
import com.datamelt.artikel.port.ProductApiInterface;
import com.datamelt.artikel.port.WebServiceInterface;
import com.datamelt.artikel.util.Constants;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.*;

public class ProductController implements ProductApiInterface
{
    private WebServiceInterface service;
    private MessageBundleInterface messages;
    private NumberFormatter numberFormatter;

    public ProductController(WebServiceInterface service, MessageBundleInterface messages, NumberFormatter numberFormatter)
    {
        this.service = service;
        this.messages = messages;
        this.numberFormatter = numberFormatter;
    }

    public Route serveAllProductsPage = (Request request, Response response) -> {
        long producerId = Long.parseLong(request.params(":producerid"));
        Producer producer = getProducerById(producerId);

        Map<String, Object> model = new HashMap<>();
        model.put("messages", messages);
        model.put("pagetitle", messages.get("PAGETITLE_PRODUCT_LIST"));
        model.put("products", getAllProducts(producerId));
        model.put("producer", producer);
        model.put("numberFormatter", numberFormatter);
        return ViewUtility.render(request,model,Path.Template.PRODUCTS);

    };

    public Route serveProductPage = (Request request, Response response) -> {
        long producerId = Long.parseLong(request.params(":producerid"));
        Producer producer = getProducerById(producerId);

        Map<String, Object> model = new HashMap<>();
        model.put("messages", messages);
        model.put("pagetitle", messages.get("PAGETITLE_PRODUCT_CHANGE"));
        model.put("fields", FormField.class);
        model.put("producer", producer);
        model.put("numberFormatter", numberFormatter);
        Optional<Product> product = Optional.ofNullable(getProductById(Long.parseLong(request.params(":id"))));
        if(product.isPresent())
        {
            model.put("form", FormConverter.convertToForm(product.get(), numberFormatter));
        }
        else
        {
            Form form = new Form();
            form.put(FormField.ID,"0");
            form.put(FormField.QUANTITY,"1");
            form.put(FormField.PRICE,"0");
            form.put(FormField.WEIGHT,"0");
            model.put("form", form);
        }
        model.put("producers", getAllProducers());
        model.put("containers", getAllProductContainers());
        model.put("origins", getAllProductOrigins());
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
            model.put("productorderitems", getShopProductOrderItems(order.get()));
            model.put("shoplabelsonly", order.get().getShopLabelsOnly());
        }
        model.put("messages", messages);
        model.put("pagetitle", messages.get("PAGETITLE_SHOP_LIST"));
        model.put("producer", producer);
        model.put("numberFormatter", numberFormatter);
        return ViewUtility.render(request,model,Path.Template.SHOPPRODUCTS);
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
        }

        Map<String, Object> model = new HashMap<>();
        ProductOrderCollection orderCollection = request.session().attribute("ordercollection");
        Optional<ProductOrder> order = Optional.ofNullable(orderCollection.get(producerId));
        if(!order.isPresent())
        {
            ProductOrderItem item = new ProductOrderItem();
            item.setProduct(getProductById(productId));
            item.setAmount(value);
            ProductOrder newOrder = new ProductOrder(producerId);
            newOrder.addOrderItem(item);
            orderCollection.add(newOrder);
        }
        else
        {
            if(order.get().getOrderItems().containsKey(productId))
            {
                ProductOrderItem shopItem = order.get().getOrderItem(productId);
                shopItem.setAmount(value);
                //shopItem.increaseAmount();
            }
            else
            {
                ProductOrderItem item = new ProductOrderItem();
                item.setProduct(getProductById(productId));
                item.setAmount(value);
                order.get().addOrderItem(item);
            }
        }

        model.put("messages", messages);
        model.put("pagetitle", messages.get("PAGETITLE_PRODUCT_LIST"));
        model.put("products", getAllProducts(producerId));
        model.put("producer", producer);
        model.put("numberFormatter", numberFormatter);
        return ViewUtility.render(request,model,Path.Template.PRODUCTS);

    };

    public Route shopProductLabels = (Request request, Response response) -> {
        long productId = Long.parseLong(request.params(":id"));
        long producerId = Long.parseLong(request.params(":producerid"));
        Producer producer = getProducerById(producerId);

        Map<String, Object> model = new HashMap<>();
        ProductOrderCollection orderCollection = request.session().attribute("ordercollection");
        Optional<ProductOrder> order = Optional.ofNullable(orderCollection.get(producerId));
        if(!order.isPresent())
        {
            ProductOrderItem item = new ProductOrderItem();
            item.setProduct(getProductById(productId));
            item.setAmount(1);
            ProductOrder newOrder = new ProductOrder(producerId, true);
            newOrder.addOrderItem(item);
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

        model.put("messages", messages);
        model.put("pagetitle", messages.get("PAGETITLE_PRODUCT_LIST"));
        model.put("products", getAllProducts(producerId));
        model.put("producer", producer);
        model.put("numberFormatter", numberFormatter);
        return ViewUtility.render(request,model,Path.Template.PRODUCTS);

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
        }

        ProductOrderCollection orderCollection = request.session().attribute("ordercollection");
        ProductOrder order = orderCollection.get(producerId);
        ProductOrderItem shopItem = order.getOrderItem(productId);
        shopItem.setAmount(value);

        Map<String, Object> model = new HashMap<>();
        model.put("messages", messages);
        model.put("pagetitle", messages.get("PAGETITLE_SHOP_LIST"));
        model.put("productorderitems", getShopProductOrderItems(order));
        model.put("producer", producer);
        model.put("numberFormatter", numberFormatter);
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
        model.put("messages", messages);
        model.put("pagetitle", messages.get("PAGETITLE_SHOP_LIST"));
        model.put("productorderitems", getShopProductOrderItems(order));
        model.put("producer", producer);
        model.put("numberFormatter", numberFormatter);
        return ViewUtility.render(request,model,Path.Template.SHOPPRODUCTS);
    };

    public Route shopProductComplete = (Request request, Response response) -> {
        long producerId = Long.parseLong(request.params(":producerid"));
        Producer producer = getProducerById(producerId);

        ProductOrderCollection orderCollection = request.session().attribute("ordercollection");
        ProductOrder order = orderCollection.get(producerId);
        if(order!=null)
        {
            addProductOrder(order);
            orderCollection.remove(producerId);
        }
        response.redirect(Path.Web.ORDERS);
        return null;
    };

    public Route serveDeleteProductPage = (Request request, Response response) -> {
        long producerId = Long.parseLong(request.params(":producerid"));
        Producer producer = getProducerById(producerId);

        Map<String, Object> model = new HashMap<>();
        model.put("messages", messages);
        model.put("pagetitle", messages.get("PAGETITLE_PRODUCT_DELETE"));
        Product product = getProductById(Long.parseLong(request.params(":id")));
        model.put("product", product);
        model.put("producer", producer);
        model.put("numberFormatter", numberFormatter);
        return ViewUtility.render(request,model,Path.Template.PRODUCT_DELETE);
    };

    public Route deleteProduct = (Request request, Response response) -> {
        long producerId = Long.parseLong(request.params(":producerid"));
        Producer producer = getProducerById(producerId);

        Map<String, Object> model = new HashMap<>();
        model.put("messages", messages);
        model.put("pagetitle", messages.get("PAGETITLE_PRODUCT_LIST"));
        String cancelled = request.queryParams("submit");
        if(!cancelled.equals(messages.get("FORM_BUTTON_CANCEL")))
        {
            deleteProduct(Long.parseLong(request.params(":id")));
        }
        model.put("products", getAllProducts(producerId));
        model.put("producer", producer);
        model.put("numberFormatter", numberFormatter);
        return ViewUtility.render(request,model,Path.Template.PRODUCTS);
    };

    public Route createLabels = (Request request, Response response) -> {
        long producerId = Long.parseLong(request.params(":producerid"));
        Producer producer = getProducerById(producerId);
        byte[] pdfOutputFile = getLabelsOutputFile(producerId);
        String fullFilename = Constants.LABELS_FILE_CONTENT_DISPOSITION_VALUE_FILENAME_PART1 + "_" + producer.getName() + Constants.LABELS_FILE_CONTENT_DISPOSITION_VALUE_FILENAME_PART2;

        response.type(Constants.LABELS_FILE_CONTENT_TYPE);
        response.header(Constants.LABELS_FILE_CONTENT_DISPOSITION_KEY,Constants.LABELS_FILE_CONTENT_DISPOSITION_VALUE + fullFilename);
        response.raw().getOutputStream().write(pdfOutputFile);
        response.raw().getOutputStream().flush();
        response.raw().getOutputStream().close();
        return null;
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
            response.type(Constants.LABELS_FILE_CONTENT_TYPE);
            response.header(Constants.LABELS_FILE_CONTENT_DISPOSITION_KEY, Constants.LABELS_FILE_CONTENT_DISPOSITION_VALUE + fullFilename);
            response.raw().getOutputStream().write(pdfOutputFile);
            response.raw().getOutputStream().flush();
            response.raw().getOutputStream().close();
            return null;
        }
        else
        {
            model.put("result", new ValidatorResult(ValidatorResult.RESULTYPE_ERROR, messages.get("ERROR_CREATING_LABELS")));
            model.put("productorderitems", getShopProductOrderItems(order));
            model.put("shoplabelsonly", order.getShopLabelsOnly());
            model.put("messages", messages);
            model.put("pagetitle", messages.get("PAGETITLE_SHOP_LIST"));
            model.put("producer", producer);
            model.put("numberFormatter", numberFormatter);
            return ViewUtility.render(request,model,Path.Template.SHOPPRODUCTS);
        }
    };

    public Route serveUpdateProductPage = (Request request, Response response) -> {
        long producerId = Long.parseLong(request.params(":producerid"));
        Producer producer = getProducerById(producerId);

        Map<String, Object> model = new HashMap<>();
        model.put("messages", messages);
        model.put("producer", producer);
        model.put("numberFormatter", numberFormatter);
        String cancelled = request.queryParams("submit");

        if(!cancelled.equals(messages.get("FORM_BUTTON_CANCEL")))
        {
            Form form = new Form();
            for(String parameter : request.queryParams())
            {
                if(FormField.exists(parameter))
                {
                    form.put(FormField.valueOf(parameter), request.queryParams(parameter));
                }
            }
            model.put("form", form);
            model.put("pagetitle", messages.get("PAGETITLE_PRODUCT_CHANGE"));
            model.put("fields",FormField.class);
            model.put("producers", getAllProducers());
            model.put("containers", getAllProductContainers());
            model.put("origins", getAllProductOrigins());

            boolean isUniqueProduct = getIsUniqueProduct(Long.parseLong(form.get(FormField.ID)),form.get(FormField.NUMBER));
            ValidatorResult result = FormValidator.validate(form, messages, isUniqueProduct, numberFormatter);
            if(result.getResultType() == ValidatorResult.RESULT_TYPE_OK)
            {
                addOrUpdateProduct(model, form);
            }
            else
            {
                model.put("result", result);
            }
            return ViewUtility.render(request,model,Path.Template.PRODUCT);
        }
        else
        {
            model.put("pagetitle", messages.get("FORM_BUTTON_CANCEL"));
            model.put("products", getAllProducts(producerId));
            return ViewUtility.render(request,model,Path.Template.PRODUCTS);
        }
    };

    private void addOrUpdateProduct(Map<String, Object> model, Form form)
    {
        if (Long.parseLong(form.get(FormField.ID)) > 0)
        {
            model.put("pagetitle", messages.get("PAGETITLE_PRODUCT_CHANGE"));
            model.put("numberFormatter", numberFormatter);
            try
            {
                updateProduct(Long.parseLong(form.get(FormField.ID)), form, numberFormatter);
                model.put("result", new ValidatorResult(messages.get("PRODUCT_FORM_CHANGED")));
            }
            catch (Exception ex)
            {
                model.put("result", new ValidatorResult(ValidatorResult.RESULTYPE_ERROR, messages.get("PRODUCT_FORM_CHANGE_ERROR")));
            }
        } else
        {
            model.put("pagetitle", messages.get("PAGETITLE_PRODUCT_ADD"));
            try
            {
                addProduct(form, numberFormatter);
                model.put("result", new ValidatorResult(messages.get("PRODUCT_FORM_ADDED")));
            }
            catch (Exception ex)
            {
                model.put("result", new ValidatorResult(ValidatorResult.RESULTYPE_ERROR, messages.get("PRODUCT_FORM_ADD_ERROR")));
            }
        }
    }

    @Override
    public List<Product> getAllProducts(long producerId) throws Exception
    {
        return service.getAllProducts(producerId);
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
        List<Product> products = getAllProducts(producerId);
        List<ProductLabel> labels = new ArrayList<>();
        for(Product product : products)
        {
            labels.add(new ProductLabel(product, messages));
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
            labels.add(new ProductLabel(product, messages));
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

}
