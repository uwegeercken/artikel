package com.datamelt.artikel.app.web.util;

import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Session;

public class Filters
{
    // If a user manually manipulates paths and forgets to add
    // a trailing slash, redirect the user to the correct path
    public static Filter addTrailingSlashes = (Request request, Response response) -> {
        if (!request.pathInfo().endsWith("/")) {
            response.redirect(request.pathInfo() + "/");
        }
    };

    public static Filter redirectToIndex = (Request request, Response response) -> {
        if (request.pathInfo().equals("/")) {
            response.redirect(Path.Web.INDEX);
        }
    };

    public static Filter redirectToLogin = (Request request, Response response) -> {
        if(!request.pathInfo().equals("/login/"))
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
