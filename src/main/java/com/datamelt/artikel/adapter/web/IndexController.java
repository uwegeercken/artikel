package com.datamelt.artikel.adapter.web;

import com.datamelt.artikel.app.web.ViewUtility;
import com.datamelt.artikel.app.web.WebApplication;
import com.datamelt.artikel.app.web.util.Message;
import com.datamelt.artikel.model.Product;
import com.datamelt.artikel.port.ProductApiInterface;
import com.datamelt.artikel.service.WebService;
import com.datamelt.artikel.app.web.util.Path;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndexController
{
    private WebService service;

    public IndexController(WebService service)
    {
        this.service = service;
    }

    public Route serveIndexPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("pagetitle", "Startseite");
        return ViewUtility.render(request,model,Path.Template.INDEX);

    };

    public Route serveAboutPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("pagetitle", "Information");
        model.put("version", WebApplication.APPLCATION_VERSION);
        model.put("lastupdate", WebApplication.APPLCATION_LAST_UPDATE);
        return ViewUtility.render(request,model,Path.Template.ABOUT);

    };

    public Route serveNotFoundPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("pagetitle", "Seite nicht gefunden");
        model.put("message", Message.PAGE_NOT_FOUND);
        response.status(HttpStatus.NOT_FOUND_404);
        return ViewUtility.render(request,model,Path.Template.NOTFOUND);
    };
}
