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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserController implements UserApiInterface
{
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
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

    public Route serveUserPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put(Constants.MODEL_FIELDS_KEY, FormField.class);
        Optional<User> user = Optional.ofNullable(getUserById(Long.parseLong(request.params(":id"))));
        if(user.isPresent())
        {
            model.put(Constants.MODEL_FORM_KEY, FormConverter.convertToForm(user.get()));
        }
        else
        {
            Form form = new Form();
            form.put(FormField.ID,"0");
            model.put(Constants.MODEL_FORM_KEY, form);
        }
        return ViewUtility.render(request,model,Path.Template.USER);

    };

    public Route serveUpdateUserPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        String cancelled = request.queryParams(Constants.FORM_SUBMIT);
        if(!cancelled.equals(WebApplication.getMessages().get("FORM_BUTTON_CANCEL")))
        {
            Form form = Form.createFormFromQueryParameters(request);
            model.put(Constants.MODEL_FORM_KEY, form);
            model.put(Constants.MODEL_FIELDS_KEY,FormField.class);

            boolean isUniqueUser = getIsUniqueUser(Long.parseLong(form.get(FormField.ID)),form.get(FormField.NAME));
            ValidatorResult result = FormValidator.validate(form, WebApplication.getMessages(), isUniqueUser, WebApplication.getNumberFormatter());
            if(result.getResultType()== ValidatorResult.RESULT_TYPE_OK)
            {
                addOrUpdateUser(model, form);
            }
            else
            {
                model.put(Constants.MODEL_RESULT_KEY, result);
            }
            return ViewUtility.render(request,model,Path.Template.USER);
        }
        else
        {
            List<User> users = getAllUsers();
            model.put(Constants.MODEL_USERS_KEY, users);
            request.session().attribute("users", users);
            return ViewUtility.render(request,model,Path.Template.USERS);
        }
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

    private void addOrUpdateUser(Map<String, Object> model, Form form)
    {
        if (Long.parseLong(form.get(FormField.ID)) > 0)
        {
            try
            {
                updateUser(Long.parseLong(form.get(FormField.ID)), form);
                model.put(Constants.MODEL_RESULT_KEY, new ValidatorResult(WebApplication.getMessages().get("USER_FORM_CHANGED")));
            }
            catch (Exception ex)
            {
                logger.error("error updating user with id [{}], error [{}]", form.get(FormField.ID), ex.getMessage());
                model.put(Constants.MODEL_RESULT_KEY, new ValidatorResult(ValidatorResult.RESULTYPE_ERROR, WebApplication.getMessages().get("USER_FORM_CHANGE_ERROR")));
            }
        } else
        {
            try
            {
                addUser(form);
                model.put(Constants.MODEL_RESULT_KEY, new ValidatorResult(WebApplication.getMessages().get("USER_FORM_ADDED")));
            }
            catch (Exception ex)
            {
                logger.error("error adding user, error [{}]", ex.getMessage());
                model.put(Constants.MODEL_RESULT_KEY, new ValidatorResult(ValidatorResult.RESULTYPE_ERROR, WebApplication.getMessages().get("USER_FORM_ADD_ERROR")));
            }
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

    @Override
    public void updateUser(long id, Form form) throws Exception
    {
        service.updateUser(id, form);
    }

    @Override
    public void addUser(Form form) throws Exception
    {
        service.addUser(form);
    }

    @Override
    public boolean getIsUniqueUser(long id, String name) throws Exception
    {
        return service.getIsUniqueUser(id, name);
    }
}
