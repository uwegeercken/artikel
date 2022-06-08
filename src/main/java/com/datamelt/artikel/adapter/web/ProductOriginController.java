package com.datamelt.artikel.adapter.web;

import com.datamelt.artikel.adapter.web.form.Form;
import com.datamelt.artikel.adapter.web.form.FormConverter;
import com.datamelt.artikel.adapter.web.form.FormField;
import com.datamelt.artikel.adapter.web.form.FormValidator;
import com.datamelt.artikel.adapter.web.validator.ValidatorResult;
import com.datamelt.artikel.app.web.ViewUtility;
import com.datamelt.artikel.app.web.util.NumberFormatter;
import com.datamelt.artikel.app.web.util.Path;
import com.datamelt.artikel.model.ProductOrigin;
import com.datamelt.artikel.port.MessageBundleInterface;
import com.datamelt.artikel.port.ProductOriginApiInterface;
import com.datamelt.artikel.port.WebServiceInterface;

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
    private MessageBundleInterface messages;
    private NumberFormatter numberFormatter;

    public ProductOriginController(WebServiceInterface service, MessageBundleInterface messages, NumberFormatter numberFormatter)
    {
        this.service = service;
        this.messages = messages;
        this.numberFormatter = numberFormatter;
    }

    public Route serveAllProductOriginsPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("messages", messages);
        model.put("pagetitle", messages.get("PAGETITLE_PRODUCTORIGIN_LIST"));
        model.put("origins", getAllProductOrigins());
        return ViewUtility.render(request,model,Path.Template.PRODUCTORIGINS);

    };

    public Route serveProductOriginPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("messages", messages);
        model.put("pagetitle", messages.get("PAGETITLE_PRODUCTORIGIN_CHANGE"));
        model.put("fields", FormField.class);
        Optional<ProductOrigin> productOrigin = Optional.ofNullable(getProductOriginById(Long.parseLong(request.params(":id"))));
        if(productOrigin.isPresent())
        {
            model.put("form", FormConverter.convertToForm(productOrigin.get()));
        }
        else
        {
            Form form = new Form();
            form.put(FormField.ID,"0");
            model.put("form", form);
        }
        return ViewUtility.render(request,model,Path.Template.PRODUCTORIGIN);

    };

    public Route serveUpdateProductOriginPage = (Request request, Response response) -> {
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
            model.put("origins", getAllProductOrigins());

            boolean isUniqueProductOrigin = getIsUniqueProductOrigin(Long.parseLong(form.get(FormField.ID)),form.get(FormField.NAME));
            ValidatorResult result = FormValidator.validate(form, messages, isUniqueProductOrigin, numberFormatter);
            if(result.getResultType() == ValidatorResult.RESULT_TYPE_OK)
            {
                addOrUpdateProductOrigin(model, form);
            }
            else
            {
                model.put("result", result);
            }
            return ViewUtility.render(request,model,Path.Template.PRODUCTORIGIN);
        }
        else
        {
            model.put("pagetitle", messages.get("FORM_BUTTON_CANCEL"));
            model.put("origins", getAllProductOrigins());
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
            model.put("pagetitle", messages.get("PAGETITLE_PRODUCTORIGIN_CHANGE"));
            try
            {
                updateProductOrigin(Long.parseLong(form.get(FormField.ID)), form);
                model.put("result", new ValidatorResult(messages.get("PRODUCT_ORIGIN_FORM_CHANGED")));
            }
            catch (Exception ex)
            {
                model.put("result", new ValidatorResult(ValidatorResult.RESULTYPE_ERROR, messages.get("PRODUCT_ORIGIN_FORM_CHANGE_ERROR")));
            }
        } else
        {
            model.put("pagetitle", messages.get("PAGETITLE_PRODUCTORIGIN_ADD"));
            try
            {
                addProductOrigin(form);
                model.put("result", new ValidatorResult(messages.get("PRODUCT_ORIGIN_FORM_ADDED")));
            }
            catch (Exception ex)
            {
                model.put("result", new ValidatorResult(ValidatorResult.RESULTYPE_ERROR, messages.get("PRODUCT_ORIGIN_FORM_ADD_ERROR")));
            }
        }
    }

}
