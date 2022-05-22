package com.datamelt.artikel.port;

import com.datamelt.artikel.model.User;

import java.util.List;

public interface UserApiInterface
{
    List<User> getAllUsers() throws Exception;
}
