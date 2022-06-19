package com.datamelt.artikel.port;

import com.datamelt.artikel.model.Market;
import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.User;

import java.util.List;
import java.util.Map;

public interface LoginApiInterface
{
    User getUserByName(String name) throws Exception;
    List<Producer> getAllProducers() throws Exception;
    long getAllProductsCount() throws Exception;
    Map<String,Long> getAllProducersProductsCount() throws Exception;
}
