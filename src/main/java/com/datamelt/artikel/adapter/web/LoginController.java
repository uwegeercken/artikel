package com.datamelt.artikel.adapter.web;

import com.datamelt.artikel.adapter.web.validator.ValidatorResult;
import com.datamelt.artikel.app.web.ViewUtility;
import com.datamelt.artikel.app.web.WebApplication;
import com.datamelt.artikel.app.web.util.HashGenerator;
import com.datamelt.artikel.app.web.util.Path;
import com.datamelt.artikel.app.web.util.Token;
import com.datamelt.artikel.config.MainConfiguration;
import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.User;
import com.datamelt.artikel.port.LoginApiInterface;
import com.datamelt.artikel.port.WebServiceInterface;
import com.datamelt.artikel.util.Constants;
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
    private MainConfiguration configuration;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    public LoginController(WebServiceInterface service, MainConfiguration configuration)
    {
        this.service = service;
        this.configuration = configuration;
    }

    public Route serveLoginPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        return ViewUtility.render(request,model,Path.Template.LOGIN);
    };

    public Route logoutUser = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        User user = request.session().attribute("user");
        request.session().removeAttribute("user");
        logger.info("user logout successful. user [{}]", user.getName());
        return ViewUtility.render(request,model,Path.Template.INDEX);

    };

    public Route authenticateUser = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();

        String username = request.queryParams("userid");
        String password = request.queryParams("password");
        Optional<User> loginUser = Optional.ofNullable(getUserByName(username));

        if(loginUser.isPresent())
        {
            boolean isAuthenticated = getUserIsAuthenticated(loginUser.get(), password);
            if (isAuthenticated)
            {
                String token = Token.generateToken(loginUser.get(),configuration.getSparkJava().getTokenExpiresMinutes());
                request.session().attribute(Constants.USERTOKEN_KEY, token);

                loginUser.get().setAuthenticated(true);
                request.session().attribute("user", loginUser.get());
                request.session().attribute("producers",getAllProducers());
                model.put("totalproductscount", getAllProductsCount());
                model.put("productcounts", getAllProducersProductsCount());
                logger.info("user login successful. user [{}]", username);
                return ViewUtility.render(request, model, Path.Template.INDEX);
            } else
            {
                loginUser.get().setAuthenticated(false);
                request.session().attribute("user", loginUser.get());
                model.put(Constants.MODEL_RESULT_KEY, new ValidatorResult(WebApplication.getMessages().get("ERROR_LOGIN_WRONG_PASSWORD")));
                logger.error("user login failed. wrong password for user [{}]", username);
                return ViewUtility.render(request, model, Path.Template.LOGIN);
            }
        }
        else
        {
            model.put(Constants.MODEL_RESULT_KEY, new ValidatorResult(WebApplication.getMessages().get("ERROR_LOGIN_UNKNOWN_USER")));
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

    @Override
    public long getAllProductsCount() throws Exception
    {
        return service.getAllProductsCount();
    }

    @Override
    public Map<String,Long> getAllProducersProductsCount() throws Exception
    {
        return service.getAllProducersProductsCount();
    }
}
