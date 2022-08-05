package com.datamelt.artikel.port;

import com.datamelt.artikel.adapter.web.form.Form;
import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.User;

import java.sql.Connection;
import java.util.List;

public interface UserApiInterface
{
    List<User> getAllUsers() throws Exception;
    User getUserById(long id) throws Exception;
    void updateUser(User user) throws Exception;
    void updateUser(long id, Form form) throws Exception;
    void addUser(Form form) throws Exception;
    boolean getIsUniqueUser(long id, String name) throws Exception;
    int sendAcl() throws Exception;
}
