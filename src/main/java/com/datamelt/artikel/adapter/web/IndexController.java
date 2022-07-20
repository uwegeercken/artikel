package com.datamelt.artikel.adapter.web;

import com.datamelt.artikel.app.web.ViewUtility;
import com.datamelt.artikel.app.web.WebApplication;
import com.datamelt.artikel.port.IndexApiInterface;
import com.datamelt.artikel.port.WebServiceInterface;
import com.datamelt.artikel.app.web.util.Path;
import com.datamelt.artikel.util.Constants;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

public class IndexController implements IndexApiInterface
{
    private WebServiceInterface service;

    public IndexController(WebServiceInterface service)
    {
        this.service = service;
    }

    public Route serveIndexPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("totalproductscount", getAllProductsCount());
        return ViewUtility.render(request,model,Path.Template.INDEX);
    };

    public Route serveAboutPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put(Constants.MODEL_VERSION_KEY, WebApplication.APPLCATION_VERSION);
        model.put(Constants.MODEL_LASTUPDATE_KEY, WebApplication.APPLCATION_LAST_UPDATE);
        return ViewUtility.render(request,model,Path.Template.ABOUT);

    };

    public Route serveNotFoundPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        response.status(HttpStatus.NOT_FOUND_404);
        return ViewUtility.render(request,model,Path.Template.NOTFOUND);
    };

    public Route serveNotAuthorizedPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        response.status(HttpStatus.METHOD_NOT_ALLOWED_405);
        return ViewUtility.render(request,model,Path.Template.NOTAUTHORIZED);
    };


    @Override
    public long getAllProductsCount() throws Exception
    {
        return service.getAllProductsCount();
    }
}
