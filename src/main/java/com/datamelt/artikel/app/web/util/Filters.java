package com.datamelt.artikel.app.web.util;

import com.datamelt.artikel.model.User;
import spark.Filter;
import spark.Request;
import spark.Response;

public class Filters
{
    public static Filter redirectToLogin = (Request request, Response response) -> {
        if(!request.pathInfo().equals(Path.Web.LOGIN) && !request.pathInfo().equals(Path.Web.ABOUT) && !request.pathInfo().equals(Path.Web.INDEX))
        {
            boolean isAuthenticated = false;
            if(request.session().attribute("user")!=null)
            {
                User user = request.session().attribute("user");
                isAuthenticated = user.isAuthenticated();

            }
            if (!isAuthenticated)
            {
                response.redirect(Path.Web.LOGIN);
            }
        }
    };
}
