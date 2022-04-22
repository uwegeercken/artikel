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
import com.datamelt.artikel.port.MessageBundleInterface;
import com.datamelt.artikel.port.ProductApiInterface;
import com.datamelt.artikel.port.WebServiceInterface;
import com.datamelt.artikel.service.WebService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProductController implements ProductApiInterface
{
    private WebServiceInterface service;
    private MessageBundleInterface messages;

    public ProductController(WebServiceInterface service, MessageBundleInterface messages)
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
        Optional<Product> product = Optional.ofNullable(getProductById(Long.parseLong(request.params(":id"))));
        if(product.isPresent())
        {
            model.put("form", ProductFormConverter.convertProduct(product.get()));
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

    public Route serveDeleteProductPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("messages", messages);
        model.put("pagetitle", messages.get("PAGETITLE_PRODUCT_DELETE"));
        Product product = getProductById(Long.parseLong(request.params(":id")));
        model.put("product", product);
        return ViewUtility.render(request,model,Path.Template.PRODUCT_DELETE);
    };

    public Route deleteProduct = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("messages", messages);
        model.put("pagetitle", messages.get("PAGETITLE_PRODUCT_LIST"));
        String cancelled = request.queryParams("submit");
        if(!cancelled.equals(messages.get("FORM_BUTTON_CANCEL")))
        {
            deleteProduct(Long.parseLong(request.params(":id")));
        }
        model.put("products", getAllProducts());
        return ViewUtility.render(request,model,Path.Template.PRODUCTS);
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
            model.put("fields",ProductFormField.class);
            model.put("producers", getAllProducers());
            model.put("containers", getAllProductContainers());
            model.put("origins", getAllProductOrigins());

            ValidatorResult result = validateProduct(form);
            if(result.getResultType()== ValidatorResult.RESULT_TYPE_OK)
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
            model.put("products", getAllProducts());
            return ViewUtility.render(request,model,Path.Template.PRODUCTS);
        }
    };

    private ValidatorResult validateProduct(ProductForm form)
    {
        ValidatorResult validateNotEmpty = ProductFormValidator.validateNotEMpty(form, messages);
        if(validateNotEmpty.getResultType() == ValidatorResult.RESULT_TYPE_OK)
        {
            ValidatorResult validateValues = ProductFormValidator.validate(form, messages);
            if(validateValues.getResultType() == ValidatorResult.RESULT_TYPE_OK)
            {
                try
                {
                    ValidatorResult validateUnique = ProductFormValidator.validateUniqueness(form, messages, getIsUniqueProduct(Long.parseLong(form.get(ProductFormField.ID)), form.get(ProductFormField.NUMBER)));
                    return validateUnique;
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    return null;
                }
            }
            else
            {
                return validateValues;
            }
        }
        else
        {
            return validateNotEmpty;
        }
    }

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
}
