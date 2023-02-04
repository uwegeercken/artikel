package com.datamelt.artikel.app.web;

import com.datamelt.artikel.adapter.web.MessageBundle;
import com.datamelt.artikel.app.web.util.*;
import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.ProductOrder;
import com.datamelt.artikel.model.ProductOrderCollection;
import com.datamelt.artikel.model.User;
import com.datamelt.artikel.util.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import spark.ModelAndView;
import spark.Request;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.Map;
import java.util.Optional;

public class ViewUtility
{
    public static String render (Request request, Map<String, Object> model, String template)
    {
        model.put(Constants.MODEL_WEBPATH_KEY, Endpoints.class);
        model.put(Constants.MODEL_TIMESTAMP_KEY, Timestamp.class);
        model.put(Constants.MODEL_NUMBER_FORMATTER_KEY, WebApplication.getNumberFormatter());
        model.put(Constants.MODEL_MESSAGES_KEY, WebApplication.getMessages());
        model.put(Constants.MODEL_PRODUCERS_KEY, request.session().attribute(Constants.SESSION_ATTRIBUTE_PRODUCERS));
        model.put(Constants.SESSION_ATTRIBUTE_PRODUCTS_NUMBER_OF_DAYS, request.session().attribute(Constants.SESSION_ATTRIBUTE_PRODUCTS_NUMBER_OF_DAYS));
        Optional<String> token = Optional.ofNullable(request.session().attribute(Constants.USERTOKEN_KEY));
        if(token.isPresent())
        {
            Jws<Claims> jws = Token.parseToken(token.get());
            model.put(Constants.MODEL_TOKEN_PAYLOAD_KEY, jws.getBody());
        }

        Optional<ProductOrderCollection> orderCollection = Optional.ofNullable(request.session().attribute(Constants.SESSION_ATTRIBUTE_ORDER_COLLECTION));
        if(!orderCollection.isPresent())
        {
            ProductOrderCollection newProductOrderCollection = new ProductOrderCollection();
            request.session().attribute(Constants.SESSION_ATTRIBUTE_ORDER_COLLECTION,newProductOrderCollection);
        }
        else
        {
            if(model.get(Constants.MODEL_PRODUCER_KEY)!=null)
            {
                Producer producer = (Producer) model.get(Constants.MODEL_PRODUCER_KEY);
                model.put(Constants.MODEL_ORDER_KEY, orderCollection.get().get(producer.getId()));
            }
        }
        return new VelocityTemplateEngine().render(new ModelAndView(model, template));
    }

    public static String renderPdf (Request request, Map<String, Object> model, String template)
    {
        return new VelocityTemplateEngine().render(new ModelAndView(model, template));
    }
}
