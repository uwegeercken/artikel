package com.datamelt.artikel.adapter.web;

import com.datamelt.artikel.adapter.web.validator.ValidatorResult;
import com.datamelt.artikel.app.web.ViewUtility;
import com.datamelt.artikel.app.web.util.HashGenerator;
import com.datamelt.artikel.app.web.util.Path;
import com.datamelt.artikel.model.User;
import com.datamelt.artikel.port.MessageBundleInterface;
import com.datamelt.artikel.port.UserApiInterface;
import com.datamelt.artikel.port.WebServiceInterface;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    public Route logoutUser = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("messages", messages);
        model.put("pagetitle", messages.get("PAGETITLE_INDEX"));
        request.session().removeAttribute("user");
        request.session().attribute("authenticated", false);
        return ViewUtility.render(request,model,Path.Template.INDEX);

    };

    public Route authenticateUser = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("messages", messages);

        String username = request.queryParams("userid");
        String password = request.queryParams("password");

        Optional<User> loginUser = Optional.ofNullable(getUserByName(username));

        if(loginUser.isPresent())
        {
            boolean isAuthenticated = getUserIsAuthenticated(loginUser.get(), password);
            if (isAuthenticated)
            {
                request.session().attribute("authenticated", true);
                request.session().attribute("user", loginUser.get());
                model.put("pagetitle", messages.get("PAGETITLE_INDEX"));
                return ViewUtility.render(request, model, Path.Template.INDEX);
            } else
            {
                request.session().attribute("authenticated", false);
                request.session().attribute("user", loginUser.get());
                model.put("pagetitle", messages.get("PAGETITLE_LOGIN"));
                model.put("result", new ValidatorResult(messages.get("ERROR_LOGIN_WRONG_PASSWORD")));
                return ViewUtility.render(request, model, Path.Template.LOGIN);
            }
        }
        else
        {
            request.session().attribute("authenticated", false);
            model.put("pagetitle", messages.get("PAGETITLE_LOGIN"));
            model.put("result", new ValidatorResult(messages.get("ERROR_LOGIN_UNKNOWN_USER")));
            return ViewUtility.render(request, model, Path.Template.LOGIN);
        }

    };

    @Override
    public User getUserByName(String name) throws Exception
    {
        return service.getUserByName(name);
    }

    private boolean getUserIsAuthenticated(User user, String loginPassword) throws Exception
    {
        String loginHashedPassword = HashGenerator.generate(user.getName(), loginPassword);

        return loginHashedPassword.equals(user.getPassword());
    }
}
