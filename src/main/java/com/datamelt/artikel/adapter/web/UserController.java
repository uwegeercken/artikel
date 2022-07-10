package com.datamelt.artikel.adapter.web;

import com.datamelt.artikel.adapter.web.form.Form;
import com.datamelt.artikel.adapter.web.form.FormConverter;
import com.datamelt.artikel.adapter.web.form.FormField;
import com.datamelt.artikel.adapter.web.form.FormValidator;
import com.datamelt.artikel.adapter.web.validator.ValidatorResult;
import com.datamelt.artikel.app.web.ViewUtility;
import com.datamelt.artikel.app.web.WebApplication;
import com.datamelt.artikel.app.web.util.HashGenerator;
import com.datamelt.artikel.app.web.util.Path;
import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.User;
import com.datamelt.artikel.port.*;
import com.datamelt.artikel.util.Constants;
import spark.Request;
import spark.Response;
import spark.Route;

import java.sql.Connection;
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

    public Route serveChangePasswordPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        User user = getUserById(Long.parseLong(request.params(":id")));
        model.put(Constants.MODEL_FORM_KEY, FormConverter.convertToForm(user));
        model.put(Constants.MODEL_FIELDS_KEY, FormField.class);
        return ViewUtility.render(request,model,Path.Template.USERS_CHANGE_PASSWORD);
    };

    public Route serveUpdatePasswordPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put(Constants.MODEL_FIELDS_KEY, FormField.class);
        String cancelled = request.queryParams(Constants.FORM_SUBMIT);
        if(!cancelled.equals(WebApplication.getMessages().get("FORM_BUTTON_CANCEL")))
        {
            User user = getUserById(Long.parseLong(request.params(":id")));
            model.put(Constants.MODEL_FORM_KEY, FormConverter.convertToForm(user));
            String password = request.queryParams(FormField.PASSWORD.toString());
            String passwordNew = request.queryParams(FormField.PASSWORD_NEW.toString());
            String passwordNewRepeated = request.queryParams(FormField.PASSWORD_NEW_REPEATED.toString());

            ValidatorResult result = checkPasswords(user.getPassword(), password, passwordNew , passwordNewRepeated);
            if(result.getResultType()== ValidatorResult.RESULT_TYPE_OK)
            {
                updateUser(user);
            }
            model.put(Constants.MODEL_RESULT_KEY, result);
            return ViewUtility.render(request,model,Path.Template.USERS_CHANGE_PASSWORD);
        }
        else
        {
            model.put(Constants.MODEL_USERS_KEY, getAllUsers());
            return ViewUtility.render(request,model,Path.Template.USERS);
        }
    };

    private ValidatorResult checkPasswords(String existingPassword, String password, String passwordNew, String passwordNewRepeated)
    {
        int counter=0;
        char[] character = passwordNew.toCharArray();
        for(char oneChracter : character)
        {
            if(Character.isDigit(oneChracter))
            {
                counter++;
            }
        }

        if(!HashGenerator.generate(password).equals(existingPassword))
        {
            return new ValidatorResult(ValidatorResult.RESULTYPE_ERROR, WebApplication.getMessages().get("ERROR_INCORRECT_PASSWORD"));
        }
        else if(passwordNew.length()<8)
        {
            return new ValidatorResult(ValidatorResult.RESULTYPE_ERROR, WebApplication.getMessages().get("ERROR_INCORRECT_PASSWORD_NEW_TOO_SHORT"));
        }
        else if(!passwordNew.equals(passwordNewRepeated))
        {
            return new ValidatorResult(ValidatorResult.RESULTYPE_ERROR, WebApplication.getMessages().get("ERROR_PASSWORD_NEW_AND_REPEATED_DIFFER"));
        }
        else if(counter==0)
        {
            return new ValidatorResult(ValidatorResult.RESULTYPE_ERROR, WebApplication.getMessages().get("ERROR_INCORRECT_PASSWORD_NEW_NO_NUMBERS"));
        }
        else
        {
            return new ValidatorResult(WebApplication.getMessages().get("INFO_PASSWORD_CHANGED"));
        }
    }

    @Override
    public List<User> getAllUsers() throws Exception
    {
        return service.getAllUsers();
    }

    @Override
    public User getUserById(long id) throws Exception
    {
        return service.getUserById(id);
    }

    @Override
    public void updateUser(User user) throws Exception
    {
        service.updateUser(user);
    }
}
