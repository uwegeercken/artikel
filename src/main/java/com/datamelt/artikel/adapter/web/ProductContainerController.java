package com.datamelt.artikel.adapter.web;

import com.datamelt.artikel.adapter.web.form.*;
import com.datamelt.artikel.adapter.web.validator.ValidatorResult;
import com.datamelt.artikel.app.web.ViewUtility;
import com.datamelt.artikel.app.web.util.NumberFormatter;
import com.datamelt.artikel.app.web.util.Path;
import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.Product;
import com.datamelt.artikel.model.ProductContainer;
import com.datamelt.artikel.port.MessageBundleInterface;
import com.datamelt.artikel.port.ProductContainerApiInterface;
import com.datamelt.artikel.port.WebServiceInterface;
import com.datamelt.artikel.service.WebService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProductContainerController implements ProductContainerApiInterface
{
    private WebServiceInterface service;
    private MessageBundleInterface messages;
    private NumberFormatter numberFormatter;

    public ProductContainerController(WebServiceInterface service, MessageBundleInterface messages, NumberFormatter numberFormatter)
    {
        this.service = service;
        this.messages = messages;
        this.numberFormatter = numberFormatter;
    }

    public Route serveAllProductContainersPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("messages", messages);
        model.put("pagetitle", messages.get("PAGETITLE_PRODUCTCONTAINER_LIST"));
        model.put("containers", getAllProductContainers());
        return ViewUtility.render(request,model,Path.Template.PRODUCTCONTAINERS);

    };

    public Route serveProductContainerPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("messages", messages);
        model.put("pagetitle", messages.get("PAGETITLE_PRODUCTCONTAINER_CHANGE"));
        model.put("fields", FormField.class);
        Optional<ProductContainer> productContainer = Optional.ofNullable(getProductContainerById(Long.parseLong(request.params(":id"))));
        if(productContainer.isPresent())
        {
            model.put("form", FormConverter.convertToForm(productContainer.get()));
        }
        else
        {
            Form form = new Form();
            form.put(FormField.ID,"0");
            model.put("form", form);
        }
        return ViewUtility.render(request,model,Path.Template.PRODUCTCONTAINER);

    };

    public Route serveUpdateProductContainerPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("messages", messages);
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
            model.put("containers", getAllProductContainers());

            boolean isUniqueProductContainer = getIsUniqueProductContainer(Long.parseLong(form.get(FormField.ID)),form.get(FormField.NAME));
            ValidatorResult result = FormValidator.validate(form, messages, isUniqueProductContainer, numberFormatter);
            if(result.getResultType() == ValidatorResult.RESULT_TYPE_OK)
            {
                addOrUpdateProductContainer(model, form);
            }
            else
            {
                model.put("result", result);
            }
            return ViewUtility.render(request,model,Path.Template.PRODUCTCONTAINER);
        }
        else
        {
            model.put("pagetitle", messages.get("FORM_BUTTON_CANCEL"));
            model.put("containers", getAllProductContainers());
            return ViewUtility.render(request,model,Path.Template.PRODUCTCONTAINERS);
        }
    };

    @Override
    public List<ProductContainer> getAllProductContainers() throws Exception
    {
        return service.getAllProductContainers();
    }

    @Override
    public ProductContainer getProductContainerById(long id) throws Exception
    {
        return service.getProductContainerById(id);
    }

    @Override
    public void updateProductContainer(long id, Form form) throws Exception
    {
        service.updateProductContainer(id, form);
    }

    @Override
    public void addProductContainer(Form form) throws Exception
    {
        service.addProductContainer(form);
    }

    @Override
    public boolean getIsUniqueProductContainer(long id, String name) throws Exception
    {
        return service.getIsUniqueProductContainer(id, name);
    }

    private void addOrUpdateProductContainer(Map<String, Object> model, Form form)
    {
        if (Long.parseLong(form.get(FormField.ID)) > 0)
        {
            model.put("pagetitle", messages.get("PAGETITLE_PRODUCTCONTAINER_CHANGE"));
            try
            {
                updateProductContainer(Long.parseLong(form.get(FormField.ID)), form);
                model.put("result", new ValidatorResult(messages.get("PRODUCT_CONTAINER_FORM_CHANGED")));
            }
            catch (Exception ex)
            {
                model.put("result", new ValidatorResult(ValidatorResult.RESULTYPE_ERROR, messages.get("PRODUCT_CONTAINER_FORM_CHANGE_ERROR")));
            }
        } else
        {
            model.put("pagetitle", messages.get("PAGETITLE_PRODUCT_CONTAINER_ADD"));
            try
            {
                addProductContainer(form);
                model.put("result", new ValidatorResult(messages.get("PRODUCT_CONTAINER_FORM_ADDED")));
            }
            catch (Exception ex)
            {
                model.put("result", new ValidatorResult(ValidatorResult.RESULTYPE_ERROR, messages.get("PRODUCT_CONTAINER_FORM_ADD_ERROR")));
            }
        }
    }

}
