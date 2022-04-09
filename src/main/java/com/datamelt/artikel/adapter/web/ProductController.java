package com.datamelt.artikel.adapter.web;

import com.datamelt.artikel.adapter.web.form.ProductForm;
import com.datamelt.artikel.adapter.web.form.ProductFormConverter;
import com.datamelt.artikel.adapter.web.form.ProductFormField;
import com.datamelt.artikel.adapter.web.validator.ValidatorResult;
import com.datamelt.artikel.adapter.web.form.ProductFormValidator;
import com.datamelt.artikel.app.web.ViewUtility;
import com.datamelt.artikel.app.web.util.Path;
import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.Product;
import com.datamelt.artikel.model.ProductContainer;
import com.datamelt.artikel.model.ProductOrigin;
import com.datamelt.artikel.port.ProductApiInterface;
import com.datamelt.artikel.service.WebService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductController implements ProductApiInterface
{
    private WebService service;
    private MessageBundle messages;

    public ProductController(WebService service, MessageBundle messages)
    {
        this.service = service;
        this.messages = messages;
    }

    public Route serveAllProductsPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("messages", messages);
        model.put("pagetitle", messages.get("PAGETITLE_PRODUCT_LIST"));
        model.put("products", getAllProducts());
        return ViewUtility.render(request,model,Path.Template.PRODUCTS);

    };

    public Route serveProductPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("messages", messages);
        model.put("pagetitle", messages.get("PAGETITLE_PRODUCT_CHANGE"));
        model.put("fields", ProductFormField.class);
        Product product = getProductById(Long.parseLong(request.params(":id")));
        if(product!=null)
        {
            model.put("form", ProductFormConverter.convertProduct(product));
        }
        else
        {
            ProductForm form = new ProductForm();
            form.put(ProductFormField.ID,"0");
            model.put("form", form);
        }
        model.put("producers", getAllProducers());
        model.put("containers", getAllProductContainers());
        model.put("origins", getAllProductOrigins());
        return ViewUtility.render(request,model,Path.Template.PRODUCT);

    };

    public Route serveProductDeletePage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("messages", messages);
        model.put("pagetitle", messages.get("PAGETITLE_PRODUCT_DELETE"));
        Product product = getProductById(Long.parseLong(request.params(":id")));
        return ViewUtility.render(request,model,Path.Template.PRODUCT_DELETE);
    };

    public Route serveUpdateProductPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("messages", messages);
        String cancelled = request.queryParams("submit");
        if(!cancelled.equals(messages.get("FORM_BUTTON_CANCEL")))
        {
            ProductForm form = new ProductForm();
            for (ProductFormField field : ProductFormField.values())
            {
                String value = request.queryParams(field.toString());
                form.put(field,value );
            }
            model.put("form", form);
            model.put("pagetitle", messages.get("PAGETITLE_PRODUCT_CHANGE"));
            ValidatorResult result = ProductFormValidator.validate(form,messages);
            if(result.getResultType()== ValidatorResult.RESULT_TYPE_OK)
            {
                addOrUpdateProduct(model, form);
            }
            else
            {
                model.put("result", result);
            }
            model.put("fields",ProductFormField.class);
            model.put("producers", getAllProducers());
            model.put("containers", getAllProductContainers());
            model.put("origins", getAllProductOrigins());
            return ViewUtility.render(request,model,Path.Template.PRODUCT);
        }
        else
        {
            model.put("pagetitle", messages.get("FORM_BUTTON_CANCEL"));
            model.put("products", getAllProducts());
            return ViewUtility.render(request,model,Path.Template.PRODUCTS);
        }
    };

    private void addOrUpdateProduct(Map<String, Object> model, ProductForm form)
    {
        if (Long.parseLong(form.get(ProductFormField.ID)) > 0)
        {
            model.put("pagetitle", messages.get("PAGETITLE_PRODUCT_CHANGE"));
            try
            {
                updateProduct(Long.parseLong(form.get(ProductFormField.ID)), form);
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
                addProduct(form);
                model.put("result", new ValidatorResult(messages.get("PRODUCT_FORM_ADDED")));
            }
            catch (Exception ex)
            {
                model.put("result", new ValidatorResult(ValidatorResult.RESULTYPE_ERROR, messages.get("PRODUCT_FORM_ADD_ERROR")));
            }
        }
    }

    @Override
    public List<Product> getAllProducts() throws Exception
    {
        return service.getAllProducts();
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
    public Product getProductById(long id) throws Exception
    {
        return service.getProductById(id);
    }

    @Override
    public void updateProduct(long id, ProductForm form) throws Exception
    {
        service.updateProduct(id, form);
    }

    @Override
    public void addProduct(ProductForm form) throws Exception
    {
        service.addProduct(form);
    }
}
