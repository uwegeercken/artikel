package com.datamelt.artikel.port;

import com.datamelt.artikel.model.Market;

import java.util.List;

public interface UserApiInterface
{
    boolean getUserIsAuthenticated(String name, String password) throws Exception;
}
