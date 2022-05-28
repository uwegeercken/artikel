package com.datamelt.artikel.app.web;

import com.datamelt.artikel.adapter.web.MessageBundle;
import com.datamelt.artikel.app.web.util.Path;
import com.datamelt.artikel.app.web.util.Timestamp;
import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.ProductOrder;
import com.datamelt.artikel.model.ProductOrderCollection;
import com.datamelt.artikel.model.User;
import spark.ModelAndView;
import spark.Request;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.Map;
import java.util.Optional;

public class ViewUtility
{
    public static String render (Request request, Map<String, Object> model, String template)
    {
        model.put("WebPath", Path.Web.class);
        model.put("Timestamp", Timestamp.class);
        model.put("producers", request.session().attribute("producers"));
        Optional<User> user = Optional.ofNullable(request.session().attribute("user"));
        if(user.isPresent())
        {
            model.put("user", user.get());
        }
        Optional<ProductOrderCollection> orderCollection = Optional.ofNullable(request.session().attribute("ordercollection"));
        if(!orderCollection.isPresent())
        {
            ProductOrderCollection newProductOrderCollection = new ProductOrderCollection();
            request.session().attribute("ordercollection",newProductOrderCollection);
        }
        else
        {
            if(model.get("producer")!=null)
            {
                Producer producer = (Producer) model.get("producer");
                model.put("order", orderCollection.get().get(producer.getId()));
            }
        }
        return new VelocityTemplateEngine().render(new ModelAndView(model, template));
    }

    public static String renderPdf (Request request, Map<String, Object> model, String template)
    {
        return new VelocityTemplateEngine().render(new ModelAndView(model, template));
    }
}
