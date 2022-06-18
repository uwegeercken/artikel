package com.datamelt.artikel.adapter.web;

import com.datamelt.artikel.app.web.ViewUtility;
import com.datamelt.artikel.app.web.util.Path;
import com.datamelt.artikel.model.User;
import com.datamelt.artikel.port.*;
import com.datamelt.artikel.util.Constants;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserController implements UserApiInterface
{
    private static WebServiceInterface service;

    public UserController(WebServiceInterface service)
    {
        this.service = service;
    }

    public Route serveAllUsersPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put(Constants.MODEL_USERS_KEY, getAllUsers());
        return ViewUtility.render(request,model,Path.Template.USERS);

    };

    @Override
    public List<User> getAllUsers() throws Exception
    {
        return service.getAllUsers();
    }
}
