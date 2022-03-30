package com.datamelt.artikel.app.web;

import spark.ModelAndView;
import spark.Request;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.Map;

public class ViewUtility
{
    public static String render (Request request, Map<String, Object> model, String template)
    {
        return new VelocityTemplateEngine().render(new ModelAndView(model, template));
    }
}
