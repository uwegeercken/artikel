package com.datamelt.artikel.app.web;

import com.datamelt.artikel.adapter.web.MessageBundle;
import com.datamelt.artikel.app.web.util.Path;
import com.datamelt.artikel.app.web.util.Timestamp;
import com.datamelt.artikel.model.ProductOrder;
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
        Optional<User> user = Optional.ofNullable(request.session().attribute("user"));
        if(user.isPresent())
        {
            model.put("user", user.get());
        }
        Optional<ProductOrder> order = Optional.ofNullable(request.session().attribute("order"));
        if(order.isPresent())
        {
            model.put("order", order.get());
        }
        return new VelocityTemplateEngine().render(new ModelAndView(model, template));
    }
}
