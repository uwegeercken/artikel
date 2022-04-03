package com.datamelt.artikel.app.web;

import com.datamelt.artikel.app.web.util.Path;
import com.datamelt.artikel.app.web.util.Timestamp;
import spark.ModelAndView;
import spark.Request;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.Map;

public class ViewUtility
{
    public static String render (Request request, Map<String, Object> model, String template)
    {
        model.put("WebPath", Path.Web.class);
        model.put("Timestamp", Timestamp.class);
        return new VelocityTemplateEngine().render(new ModelAndView(model, template));
    }
}
