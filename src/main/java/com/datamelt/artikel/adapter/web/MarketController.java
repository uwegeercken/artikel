package com.datamelt.artikel.adapter.web;

import com.datamelt.artikel.adapter.web.form.Form;
import com.datamelt.artikel.adapter.web.form.FormConverter;
import com.datamelt.artikel.adapter.web.form.FormField;
import com.datamelt.artikel.adapter.web.form.FormValidator;
import com.datamelt.artikel.adapter.web.validator.ValidatorResult;
import com.datamelt.artikel.app.web.ViewUtility;
import com.datamelt.artikel.app.web.WebApplication;
import com.datamelt.artikel.app.web.util.Path;
import com.datamelt.artikel.model.Market;
import com.datamelt.artikel.model.MarketType;
import com.datamelt.artikel.port.MarketApiInterface;
import com.datamelt.artikel.port.WebServiceInterface;
import com.datamelt.artikel.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.*;

public class MarketController implements MarketApiInterface
{
    private static final Logger logger = LoggerFactory.getLogger(MarketController.class);
    private static WebServiceInterface service;


    public MarketController(WebServiceInterface service)
    {
        this.service = service;
    }

    public Route serveAllMarketsPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put(Constants.MODEL_MARKETS_KEY, getAllMarkets());
        return ViewUtility.render(request,model,Path.Template.MARKETS);

    };

    public Route serveMarketPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put(Constants.MODEL_FIELDS_KEY, FormField.class);
        model.put(Constants.MODEL_MARKET_TYPES_KEY, getAllTypes());
        Optional<Market> market = Optional.ofNullable(getMarketById(Long.parseLong(request.params(":id"))));
        if(market.isPresent())
        {
            model.put(Constants.MODEL_FORM_KEY, FormConverter.convertToForm(market.get()));
        }
        else
        {
            Form form = new Form();
            form.put(FormField.ID,"0");
            model.put(Constants.MODEL_FORM_KEY, form);

        }
        return ViewUtility.render(request,model,Path.Template.MARKET);

    };

    public Route serveDeleteMarketPage = (Request request, Response response) -> {
        Market market = getMarketById(Long.parseLong(request.params(":id")));
        Map<String, Object> model = new HashMap<>();
        model.put(Constants.MODEL_MARKET_KEY, market);
        return ViewUtility.render(request,model,Path.Template.MARKET_DELETE);
    };

    public Route deleteMarket = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        String cancelled = request.queryParams(Constants.FORM_SUBMIT);
        if(!cancelled.equals(WebApplication.getMessages().get("FORM_BUTTON_CANCEL")))
        {
            deleteMarket(Long.parseLong(request.params(":id")));
        }
        model.put(Constants.MODEL_MARKETS_KEY, getAllMarkets());
        return ViewUtility.render(request,model,Path.Template.MARKETS);
    };

    public Route serveUpdateMarketPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put(Constants.MODEL_MARKET_TYPES_KEY, getAllTypes());
        String cancelled = request.queryParams(Constants.FORM_SUBMIT);

        if(!cancelled.equals(WebApplication.getMessages().get("FORM_BUTTON_CANCEL")))
        {
            Form form = Form.createFormFromQueryParameters(request);
            model.put(Constants.MODEL_FORM_KEY, form);
            model.put(Constants.MODEL_FIELDS_KEY,FormField.class);
            model.put(Constants.MODEL_MARKETS_KEY, getAllMarkets());

            boolean isUniqueMarket = getIsUniqueMarket(Long.parseLong(form.get(FormField.ID)),form.get(FormField.NAME));
            ValidatorResult result = FormValidator.validate(form, WebApplication.getMessages(), isUniqueMarket, WebApplication.getNumberFormatter());
            if(result.getResultType() == ValidatorResult.RESULT_TYPE_OK)
            {
                addOrUpdateMarket(model, form);
            }
            else
            {
                model.put(Constants.MODEL_RESULT_KEY, result);
            }
            return ViewUtility.render(request,model,Path.Template.MARKET);
        }
        else
        {
            model.put(Constants.MODEL_MARKETS_KEY, getAllMarkets());
            return ViewUtility.render(request,model,Path.Template.MARKETS);
        }
    };

    private List<MarketType> getAllTypes()
    {
        List<MarketType> types = new ArrayList<>();
        types.add(new MarketType(0, WebApplication.getMessages().get("MARKET_TYPE_REGULAR_SELLING")));
        types.add(new MarketType(1,WebApplication.getMessages().get("MARKET_TYPE_HOMEBASE_SELLING")));
        return types;
    }

    @Override
    public List<Market> getAllMarkets() throws Exception
    {
        return service.getAllMarkets();
    }

    @Override
    public Market getMarketById(long id) throws Exception
    {
        return service.getMarketById(id);
    }

    @Override
    public void deleteMarket(long id) throws Exception
    {
        service.deleteMarket(id);
    }

    @Override
    public void updateMarket(long id, Form form) throws Exception
    {
        service.updateMarket(id, form);
    }

    @Override
    public void addMarket(Form form) throws Exception
    {
        service.addMarket(form);
    }

    @Override
    public boolean getIsUniqueMarket(long id, String name) throws Exception
    {
        return service.getIsUniqueMarket(id, name);
    }

    private void addOrUpdateMarket(Map<String, Object> model, Form form)
    {
        if (Long.parseLong(form.get(FormField.ID)) > 0)
        {
            try
            {
                updateMarket(Long.parseLong(form.get(FormField.ID)), form);
                model.put(Constants.MODEL_RESULT_KEY, new ValidatorResult(WebApplication.getMessages().get("MARKET_FORM_CHANGED")));
            }
            catch (Exception ex)
            {
                logger.error("error updating market with id [{}], error [{}]", form.get(FormField.ID), ex.getMessage());
                model.put(Constants.MODEL_RESULT_KEY, new ValidatorResult(ValidatorResult.RESULTYPE_ERROR, WebApplication.getMessages().get("MARKET_FORM_CHANGE_ERROR")));
            }
        }
        else
        {
            try
            {
                addMarket(form);
                model.put(Constants.MODEL_RESULT_KEY, new ValidatorResult(WebApplication.getMessages().get("MARKET_FORM_ADDED")));
            }
            catch (Exception ex)
            {
                logger.error("error adding market, error [{}]", ex.getMessage());
                model.put(Constants.MODEL_RESULT_KEY, new ValidatorResult(ValidatorResult.RESULTYPE_ERROR, WebApplication.getMessages().get("MARKET_FORM_ADD_ERROR")));
            }
        }
    }

}
