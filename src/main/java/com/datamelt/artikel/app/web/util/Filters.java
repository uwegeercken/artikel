package com.datamelt.artikel.app.web.util;

import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Session;

public class Filters
{
    public static Filter redirectToLogin = (Request request, Response response) -> {
        if(!request.pathInfo().equals(Path.Web.LOGIN) && !request.pathInfo().equals(Path.Web.ABOUT) && !request.pathInfo().equals(Path.Web.INDEX))
        {
            boolean isAuthenticated = false;
            if(request.session().attribute("authenticated")!=null)
            {
                isAuthenticated = request.session().attribute("authenticated");

            }
            if (!isAuthenticated)
            {
                response.redirect(Path.Web.LOGIN);
            }
        }
    };
}
