package com.datamelt.artikel.adapter.web;

import com.datamelt.artikel.app.web.ViewUtility;
import com.datamelt.artikel.app.web.util.Path;
import com.datamelt.artikel.model.User;
import com.datamelt.artikel.port.*;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserController implements UserApiInterface
{
    private static WebServiceInterface service;
    private MessageBundleInterface messages;

    public UserController(WebServiceInterface service, MessageBundleInterface messages)
    {
        this.service = service;
        this.messages = messages;
    }

    public Route serveAllUsersPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("messages", messages);
        model.put("pagetitle", messages.get("PAGETITLE_USER_LIST"));
        model.put("users", getAllUsers());
        return ViewUtility.render(request,model,Path.Template.USERS);

    };

    @Override
    public List<User> getAllUsers() throws Exception
    {
        return service.getAllUsers();
    }
}
