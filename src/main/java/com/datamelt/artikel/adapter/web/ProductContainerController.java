package com.datamelt.artikel.adapter.web;

import com.datamelt.artikel.adapter.web.form.*;
import com.datamelt.artikel.adapter.web.validator.ValidatorResult;
import com.datamelt.artikel.app.web.ViewUtility;
import com.datamelt.artikel.app.web.WebApplication;
import com.datamelt.artikel.app.web.util.Path;
import com.datamelt.artikel.model.ProductContainer;
import com.datamelt.artikel.port.ProductContainerApiInterface;
import com.datamelt.artikel.port.WebServiceInterface;
import com.datamelt.artikel.util.Constants;
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

    public ProductContainerController(WebServiceInterface service)
    {
        this.service = service;
    }

    public Route serveAllProductContainersPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("containers", getAllProductContainers());
        return ViewUtility.render(request,model,Path.Template.PRODUCTCONTAINERS);

    };

    public Route serveProductContainerPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
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
        String cancelled = request.queryParams(Constants.FORM_SUBMIT);

        if(!cancelled.equals(WebApplication.getMessages().get("FORM_BUTTON_CANCEL")))
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
            model.put("fields",FormField.class);
            model.put("containers", getAllProductContainers());

            boolean isUniqueProductContainer = getIsUniqueProductContainer(Long.parseLong(form.get(FormField.ID)),form.get(FormField.NAME));
            ValidatorResult result = FormValidator.validate(form, WebApplication.getMessages(), isUniqueProductContainer, WebApplication.getNumberFormatter());
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
            try
            {
                updateProductContainer(Long.parseLong(form.get(FormField.ID)), form);
                model.put("result", new ValidatorResult(WebApplication.getMessages().get("PRODUCT_CONTAINER_FORM_CHANGED")));
            }
            catch (Exception ex)
            {
                model.put("result", new ValidatorResult(ValidatorResult.RESULTYPE_ERROR, WebApplication.getMessages().get("PRODUCT_CONTAINER_FORM_CHANGE_ERROR")));
            }
        } else
        {
            try
            {
                addProductContainer(form);
                model.put("result", new ValidatorResult(WebApplication.getMessages().get("PRODUCT_CONTAINER_FORM_ADDED")));
            }
            catch (Exception ex)
            {
                model.put("result", new ValidatorResult(ValidatorResult.RESULTYPE_ERROR, WebApplication.getMessages().get("PRODUCT_CONTAINER_FORM_ADD_ERROR")));
            }
        }
    }

}
