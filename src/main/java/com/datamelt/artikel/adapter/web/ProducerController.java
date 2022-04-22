package com.datamelt.artikel.adapter.web;

import com.datamelt.artikel.adapter.web.form.*;
import com.datamelt.artikel.adapter.web.validator.ValidatorResult;
import com.datamelt.artikel.app.web.ViewUtility;
import com.datamelt.artikel.app.web.util.Path;
import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.port.ProducerApiInterface;
import com.datamelt.artikel.service.WebService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProducerController implements ProducerApiInterface
{
    private static WebService service;
    private MessageBundle messages;

    public ProducerController(WebService service, MessageBundle messages)
    {
        this.service = service;
        this.messages = messages;
    }

    public Route serveAllProducersPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("messages", messages);
        model.put("pagetitle", messages.get("PAGETITLE_PRODUCER_LIST"));
        model.put("producers", getAllProducers());
        return ViewUtility.render(request,model,Path.Template.PRODUCERS);
    };

    public Route serveProducerPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("messages", messages);
        model.put("pagetitle", messages.get("PAGETITLE_PRODUCER_CHANGE"));
        model.put("fields", ProducerFormField.class);
        Producer producer = getProducerById(Long.parseLong(request.params(":id")));
        if(producer!=null)
        {
            model.put("form", ProducerFormConverter.convertProducer(producer));
        }
        else
        {
            ProducerForm form = new ProducerForm();
            form.put(ProducerFormField.ID,"0");
            model.put("form", form);
        }
        return ViewUtility.render(request,model,Path.Template.PRODUCER);

    };

    public Route serveUpdateProducerPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("messages", messages);
        String cancelled = request.queryParams("submit");
        if(!cancelled.equals(messages.get("FORM_BUTTON_CANCEL")))
        {
            ProducerForm form = new ProducerForm();
            for (ProducerFormField field : ProducerFormField.values())
            {
                String value = request.queryParams(field.toString());
                form.put(field,value );
            }
            model.put("form", form);
            model.put("pagetitle", messages.get("PAGETITLE_PRODUCER_CHANGE"));
            model.put("fields",ProducerFormField.class);

            ValidatorResult result = validateProducer(form);
            if(result.getResultType()== ValidatorResult.RESULT_TYPE_OK)
            {
                addOrUpdateProducer(model, form);
            }
            else
            {
                model.put("result", result);
            }
            return ViewUtility.render(request,model,Path.Template.PRODUCER);
        }
        else
        {
            model.put("pagetitle", messages.get("FORM_BUTTON_CANCEL"));
            model.put("producers", getAllProducers());
            return ViewUtility.render(request,model,Path.Template.PRODUCERS);
        }
    };

    public Route serveDeleteProducerPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("messages", messages);
        model.put("pagetitle", messages.get("PAGETITLE_PRODUCER_DELETE"));
        Producer producer = getProducerById(Long.parseLong(request.params(":id")));
        model.put("producer", producer);
        return ViewUtility.render(request,model,Path.Template.PRODUCER_DELETE);
    };

    public Route deleteProducer = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("messages", messages);
        model.put("pagetitle", messages.get("PAGETITLE_PRODUCER_LIST"));
        String cancelled = request.queryParams("submit");
        if(!cancelled.equals(messages.get("FORM_BUTTON_CANCEL")))
        {
            deleteProducer(Long.parseLong(request.params(":id")));
        }
        model.put("producers", getAllProducers());
        return ViewUtility.render(request,model,Path.Template.PRODUCERS);
    };
    private ValidatorResult validateProducer(ProducerForm form)
    {
        ValidatorResult validateNotEmpty = ProducerFormValidator.validateNotEMpty(form, messages);
        if(validateNotEmpty.getResultType() == ValidatorResult.RESULT_TYPE_OK)
        {
            ValidatorResult validateValues = ProducerFormValidator.validate(form, messages);
            if(validateValues.getResultType() == ValidatorResult.RESULT_TYPE_OK)
            {
                try
                {
                    ValidatorResult validateUnique = ProducerFormValidator.validateUniqueness(form, messages, getIsUniqueProducer(Long.parseLong(form.get(ProducerFormField.ID)), form.get(ProducerFormField.NAME)));
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

    private void addOrUpdateProducer(Map<String, Object> model, ProducerForm form)
    {
        if (Long.parseLong(form.get(ProducerFormField.ID)) > 0)
        {
            model.put("pagetitle", messages.get("PAGETITLE_PRODUCER_CHANGE"));
            try
            {
                updateProducer(Long.parseLong(form.get(ProducerFormField.ID)), form);
                model.put("result", new ValidatorResult(messages.get("PRODUCER_FORM_CHANGED")));
            }
            catch (Exception ex)
            {
                model.put("result", new ValidatorResult(ValidatorResult.RESULTYPE_ERROR, messages.get("PRODUCER_FORM_CHANGE_ERROR")));
            }
        } else
        {
            model.put("pagetitle", messages.get("PAGETITLE_PRODUCER_ADD"));
            try
            {
                addProducer(form);
                model.put("result", new ValidatorResult(messages.get("PRODUCER_FORM_ADDED")));
            }
            catch (Exception ex)
            {
                model.put("result", new ValidatorResult(ValidatorResult.RESULTYPE_ERROR, messages.get("PRODUCER_FORM_ADD_ERROR")));
            }
        }
    }

    @Override
    public List<Producer> getAllProducers() throws Exception
    {
        return service.getAllProducers();
    }

    @Override
    public Producer getProducerById(long id) throws Exception
    {
        return service.getProducerById(id);
    }

    @Override
    public void updateProducer(long id, ProducerForm form) throws Exception
    {
        service.updateProducer(id, form);
    }

    @Override
    public void addProducer(ProducerForm form) throws Exception
    {

    }

    @Override
    public boolean getIsUniqueProducer(long id, String name) throws Exception
    {
        return service.getIsUniqueProducer(id, name);
    }

    @Override
    public void deleteProducer(long id) throws Exception
    {
        service.deleteProducer(id);
    }
}
