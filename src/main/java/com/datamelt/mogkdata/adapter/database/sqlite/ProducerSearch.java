package com.datamelt.mogkdata.adapter.database.sqlite;

import com.datamelt.mogkdata.model.Producer;

import java.sql.Connection;
import java.util.List;

class ProducerSearch
{
    public static Producer getProducerById(Connection connection, long id) throws Exception
    {
        List<Producer> producers = CollectionHandler.getAllProducers(connection);
        for(Producer producer : producers)
        {
            if(producer.getId()== id)
            {
                return producer;
            }
        }
        return null;
    }

    public static Producer getProducerByName(Connection connection, String name) throws Exception
    {
        List<Producer> producers = CollectionHandler.getAllProducers(connection);
        for(Producer producer : producers)
        {
            if(producer.getName().equals(name))
            {
                return producer;
            }
        }
        return null;
    }
}
