package com.datamelt.artikel.port;

import com.datamelt.artikel.model.Market;
import com.datamelt.artikel.model.User;

import java.util.List;

public interface UserApiInterface
{
    User getUserByName(String name) throws Exception;
}
