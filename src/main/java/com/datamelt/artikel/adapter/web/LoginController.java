package com.datamelt.artikel.adapter.web;

import com.datamelt.artikel.adapter.web.validator.ValidatorResult;
import com.datamelt.artikel.app.web.ViewUtility;
import com.datamelt.artikel.app.web.WebApplication;
import com.datamelt.artikel.app.web.util.Path;
import com.datamelt.artikel.port.MessageBundleInterface;
import com.datamelt.artikel.port.UserApiInterface;
import com.datamelt.artikel.port.WebServiceInterface;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

public class LoginController implements UserApiInterface
{
    private WebServiceInterface service;
    private MessageBundleInterface messages;

    public LoginController(WebServiceInterface service, MessageBundleInterface messages)
    {
        this.service = service;
        this.messages = messages;
    }

    public Route serveLoginPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("messages", messages);
        model.put("pagetitle", messages.get("PAGETITLE_LOGIN"));
        return ViewUtility.render(request,model,Path.Template.LOGIN);

    };

    public Route authenticateUser = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("messages", messages);

        String username = request.queryParams("userid");
        String password = request.queryParams("password");

        boolean isAuthenticated = getUserIsAuthenticated(username, password);

        if(isAuthenticated)
        {
            request.session().attribute("authenticated", true);
            model.put("pagetitle", messages.get("PAGETITLE_INDEX"));

            return ViewUtility.render(request, model, Path.Template.INDEX);
        }
        else
        {
            request.session().attribute("authenticated", false);
            model.put("pagetitle", messages.get("PAGETITLE_LOGIN"));
            model.put("result", new ValidatorResult(messages.get("ERROR_DURING_LOGIN")));
            return ViewUtility.render(request,model,Path.Template.LOGIN);
        }

    };


    @Override
    public boolean getUserIsAuthenticated(String name, String password) throws Exception
    {
        return service.getUserIsAuthenticated(name, password);
    }
}
