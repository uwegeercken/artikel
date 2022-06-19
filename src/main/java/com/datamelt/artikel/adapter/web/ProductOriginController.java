package com.datamelt.artikel.adapter.web;

import com.datamelt.artikel.adapter.web.form.Form;
import com.datamelt.artikel.adapter.web.form.FormConverter;
import com.datamelt.artikel.adapter.web.form.FormField;
import com.datamelt.artikel.adapter.web.form.FormValidator;
import com.datamelt.artikel.adapter.web.validator.ValidatorResult;
import com.datamelt.artikel.app.web.ViewUtility;
import com.datamelt.artikel.app.web.WebApplication;
import com.datamelt.artikel.app.web.util.Path;
import com.datamelt.artikel.model.ProductOrigin;
import com.datamelt.artikel.port.ProductOriginApiInterface;
import com.datamelt.artikel.port.WebServiceInterface;

import com.datamelt.artikel.util.Constants;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProductOriginController implements ProductOriginApiInterface
{
    private WebServiceInterface service;

    public ProductOriginController(WebServiceInterface service)
    {
        this.service = service;
    }

    public Route serveAllProductOriginsPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put(Constants.MODEL_ORIGINS_KEY, getAllProductOrigins());
        return ViewUtility.render(request,model,Path.Template.PRODUCTORIGINS);

    };

    public Route serveProductOriginPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put(Constants.MODEL_FIELDS_KEY, FormField.class);
        Optional<ProductOrigin> productOrigin = Optional.ofNullable(getProductOriginById(Long.parseLong(request.params(":id"))));
        if(productOrigin.isPresent())
        {
            model.put(Constants.MODEL_FORM_KEY, FormConverter.convertToForm(productOrigin.get()));
        }
        else
        {
            Form form = new Form();
            form.put(FormField.ID,"0");
            model.put(Constants.MODEL_FORM_KEY, form);
        }
        return ViewUtility.render(request,model,Path.Template.PRODUCTORIGIN);

    };

    public Route serveUpdateProductOriginPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        String cancelled = request.queryParams(Constants.FORM_SUBMIT);

        if(!cancelled.equals(WebApplication.getMessages().get("FORM_BUTTON_CANCEL")))
        {
            Form form = Form.createFormFromQueryParameters(request);
            model.put(Constants.MODEL_FORM_KEY, form);
            model.put(Constants.MODEL_FIELDS_KEY,FormField.class);
            model.put(Constants.MODEL_ORIGINS_KEY, getAllProductOrigins());

            boolean isUniqueProductOrigin = getIsUniqueProductOrigin(Long.parseLong(form.get(FormField.ID)),form.get(FormField.NAME));
            ValidatorResult result = FormValidator.validate(form, WebApplication.getMessages(), isUniqueProductOrigin, WebApplication.getNumberFormatter());
            if(result.getResultType() == ValidatorResult.RESULT_TYPE_OK)
            {
                addOrUpdateProductOrigin(model, form);
            }
            else
            {
                model.put(Constants.MODEL_RESULT_KEY, result);
            }
            return ViewUtility.render(request,model,Path.Template.PRODUCTORIGIN);
        }
        else
        {
            model.put(Constants.MODEL_ORIGINS_KEY, getAllProductOrigins());
            return ViewUtility.render(request,model,Path.Template.PRODUCTORIGINS);
        }
    };


    @Override
    public List<ProductOrigin> getAllProductOrigins() throws Exception
    {
        return service.getAllProductOrigins();
    }

    @Override
    public ProductOrigin getProductOriginById(long id) throws Exception
    {
        return service.getProductOriginById(id);
    }

    @Override
    public void updateProductOrigin(long id, Form form) throws Exception
    {
        service.updateProductOrigin(id, form);
    }

    @Override
    public void addProductOrigin(Form form) throws Exception
    {
        service.addProductOrigin(form);
    }

    @Override
    public boolean getIsUniqueProductOrigin(long id, String name) throws Exception
    {
        return service.getIsUniqueProductOrigin(id, name);
    }

    private void addOrUpdateProductOrigin(Map<String, Object> model, Form form)
    {
        if (Long.parseLong(form.get(FormField.ID)) > 0)
        {
            try
            {
                updateProductOrigin(Long.parseLong(form.get(FormField.ID)), form);
                model.put(Constants.MODEL_RESULT_KEY, new ValidatorResult(WebApplication.getMessages().get("PRODUCT_ORIGIN_FORM_CHANGED")));
            }
            catch (Exception ex)
            {
                model.put(Constants.MODEL_RESULT_KEY, new ValidatorResult(ValidatorResult.RESULTYPE_ERROR, WebApplication.getMessages().get("PRODUCT_ORIGIN_FORM_CHANGE_ERROR")));
            }
        } else
        {
            try
            {
                addProductOrigin(form);
                model.put(Constants.MODEL_RESULT_KEY, new ValidatorResult(WebApplication.getMessages().get("PRODUCT_ORIGIN_FORM_ADDED")));
            }
            catch (Exception ex)
            {
                model.put(Constants.MODEL_RESULT_KEY, new ValidatorResult(ValidatorResult.RESULTYPE_ERROR, WebApplication.getMessages().get("PRODUCT_ORIGIN_FORM_ADD_ERROR")));
            }
        }
    }

}
