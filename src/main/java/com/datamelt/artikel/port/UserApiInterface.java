package com.datamelt.artikel.port;

import com.datamelt.artikel.model.User;

import java.sql.Connection;
import java.util.List;

public interface UserApiInterface
{
    List<User> getAllUsers() throws Exception;
    User getUserById(long id) throws Exception;
    void updateUser(User user) throws Exception;
}
