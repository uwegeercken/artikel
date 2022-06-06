package com.datamelt.artikel.adapter.web;

import com.datamelt.artikel.adapter.web.validator.ValidatorResult;
import com.datamelt.artikel.app.web.ViewUtility;
import com.datamelt.artikel.app.web.util.HashGenerator;
import com.datamelt.artikel.app.web.util.Path;
import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.User;
import com.datamelt.artikel.port.MessageBundleInterface;
import com.datamelt.artikel.port.LoginApiInterface;
import com.datamelt.artikel.port.WebServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class LoginController implements LoginApiInterface
{
    private WebServiceInterface service;
    private MessageBundleInterface messages;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

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
                loginUser.get().setAuthenticated(true);
                request.session().attribute("user", loginUser.get());
                model.put("pagetitle", messages.get("PAGETITLE_INDEX"));
                request.session().attribute("producers",getAllProducers());
                logger.info("user login successful. user [{}]", username);
                return ViewUtility.render(request, model, Path.Template.INDEX);
            } else
            {
                loginUser.get().setAuthenticated(false);
                request.session().attribute("user", loginUser.get());
                model.put("pagetitle", messages.get("PAGETITLE_LOGIN"));
                model.put("result", new ValidatorResult(messages.get("ERROR_LOGIN_WRONG_PASSWORD")));
                logger.error("user login failed. wrong password for user [{}]", username);
                return ViewUtility.render(request, model, Path.Template.LOGIN);
            }
        }
        else
        {
            model.put("pagetitle", messages.get("PAGETITLE_LOGIN"));
            model.put("result", new ValidatorResult(messages.get("ERROR_LOGIN_UNKNOWN_USER")));
            logger.error("user login failed. user not existing [{}]", username);
            return ViewUtility.render(request, model, Path.Template.LOGIN);
        }
    };

    @Override
    public User getUserByName(String name) throws Exception
    {
        return service.getUserByName(name);
    }

    @Override
    public List<Producer> getAllProducers() throws Exception
    {
        return service.getAllProducers();
    }

    private boolean getUserIsAuthenticated(User user, String loginPassword) throws Exception
    {
        String loginHashedPassword = HashGenerator.generate(loginPassword);
        return loginHashedPassword.equals(user.getPassword());
    }
}
