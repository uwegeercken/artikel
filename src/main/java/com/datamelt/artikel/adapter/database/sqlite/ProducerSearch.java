package com.datamelt.artikel.adapter.database.sqlite;

import com.datamelt.artikel.model.Producer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class ProducerSearch
{

    private static final String SQL_QUERY_BY_ID = "select * from producer where id=?";
    private static final String SQL_QUERY_BY_NAME = "select * from producer where name=?";
    private static final String SQL_QUERY_EXISTS = "select count(1) as counter from producer where name=?";

    static Producer getProducerById(Connection connection, long id) throws Exception
    {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_BY_ID);
        statement.setLong(1, id);
        ResultSet resultset = statement.executeQuery();
        Producer producer = null;
        if(resultset.next())
        {
            producer = new Producer(resultset.getString("name"));
            producer.setId(resultset.getLong("id"));
            producer.setNoOrdering(resultset.getLong("no_ordering"));
        }
        statement.clearParameters();
        resultset.close();
        statement.close();
        return producer;
    }

    static Producer getProducerByName(Connection connection, String name) throws Exception
    {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_BY_NAME);
        statement.setString(1, name);
        ResultSet resultset = statement.executeQuery();
        Producer producer = null;
        if(resultset.next())
        {
            producer = new Producer(resultset.getString("name"));
            producer.setId(resultset.getLong("id"));
            producer.setNoOrdering(resultset.getLong("no_ordering"));
        }
        statement.clearParameters();
        resultset.close();
        statement.close();
        return producer;
    }

    static boolean getExistProducer(Connection connection, String name) throws Exception
    {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_EXISTS);
        statement.setString(1, name);
        ResultSet resultset = statement.executeQuery();
        boolean exist = false;
        if(resultset.next())
        {
            exist = resultset.getLong("counter") == 1;
        }
        statement.clearParameters();
        resultset.close();
        statement.close();
        return exist;
    }
}
