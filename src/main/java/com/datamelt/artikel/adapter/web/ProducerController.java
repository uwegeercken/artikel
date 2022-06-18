package com.datamelt.artikel.adapter.web;

import com.datamelt.artikel.adapter.web.form.*;
import com.datamelt.artikel.adapter.web.validator.ValidatorResult;
import com.datamelt.artikel.app.web.ViewUtility;
import com.datamelt.artikel.app.web.WebApplication;
import com.datamelt.artikel.app.web.util.Path;
import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.port.ProducerApiInterface;
import com.datamelt.artikel.port.WebServiceInterface;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProducerController implements ProducerApiInterface
{
    private WebServiceInterface service;

    public ProducerController(WebServiceInterface service)
    {
        this.service = service;
    }

    public Route serveAllProducersPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("producers", getAllProducers());
        return ViewUtility.render(request,model, Path.Template.PRODUCERS);
    };

    public Route serveProducerPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("fields", FormField.class);
        Optional<Producer> producer = Optional.ofNullable(getProducerById(Long.parseLong(request.params(":id"))));
        if(producer.isPresent())
        {
            model.put("form", FormConverter.convertToForm(producer.get()));
        }
        else
        {
            Form form = new Form();
            form.put(FormField.ID,"0");
            model.put("form", form);
        }
        return ViewUtility.render(request,model,Path.Template.PRODUCER);

    };

    public Route serveUpdateProducerPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        String cancelled = request.queryParams("submit");
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

            boolean isUniqueProducer = getIsUniqueProducer(Long.parseLong(form.get(FormField.ID)),form.get(FormField.NAME));
            ValidatorResult result = FormValidator.validate(form, WebApplication.getMessages(), isUniqueProducer, WebApplication.getNumberFormatter());
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
            List<Producer> producers = getAllProducers();
            model.put("producers", producers);
            request.session().attribute("producers", producers);
            return ViewUtility.render(request,model,Path.Template.PRODUCERS);
        }
    };

    public Route serveDeleteProducerPage = (Request request, Response response) -> {
        Producer producer = getProducerById(Long.parseLong(request.params(":id")));
        Map<String, Object> model = new HashMap<>();
        model.put("producer", producer);
        return ViewUtility.render(request,model,Path.Template.PRODUCER_DELETE);
    };

    public Route deleteProducer = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        String cancelled = request.queryParams("submit");
        if(!cancelled.equals(WebApplication.getMessages().get("FORM_BUTTON_CANCEL")))
        {
            deleteProducer(Long.parseLong(request.params(":id")));
        }
        List<Producer> producers = getAllProducers();
        model.put("producers", producers);
        request.session().attribute("producers", producers);
        return ViewUtility.render(request,model,Path.Template.PRODUCERS);
    };

    private void addOrUpdateProducer(Map<String, Object> model, Form form)
    {
        if (Long.parseLong(form.get(FormField.ID)) > 0)
        {
            try
            {
                updateProducer(Long.parseLong(form.get(FormField.ID)), form);
                model.put("result", new ValidatorResult(WebApplication.getMessages().get("PRODUCER_FORM_CHANGED")));
            }
            catch (Exception ex)
            {
                model.put("result", new ValidatorResult(ValidatorResult.RESULTYPE_ERROR, WebApplication.getMessages().get("PRODUCER_FORM_CHANGE_ERROR")));
            }
        } else
        {
            try
            {
                addProducer(form);
                model.put("result", new ValidatorResult(WebApplication.getMessages().get("PRODUCER_FORM_ADDED")));
            }
            catch (Exception ex)
            {
                model.put("result", new ValidatorResult(ValidatorResult.RESULTYPE_ERROR, WebApplication.getMessages().get("PRODUCER_FORM_ADD_ERROR")));
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
    public void updateProducer(long id, Form form) throws Exception
    {
        service.updateProducer(id, form);
    }

    @Override
    public void addProducer(Form form) throws Exception
    {
        service.addProducer(form);
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
