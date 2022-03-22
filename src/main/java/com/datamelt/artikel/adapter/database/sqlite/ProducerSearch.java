package com.datamelt.artikel.adapter.database.sqlite;

import com.datamelt.artikel.model.Order;
import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

class ProducerSearch
{
    public static Producer getProducerById(Connection connection, long id) throws Exception
    {
        PreparedStatement queryStatement = connection.prepareStatement("select * from producer where id=?");
        queryStatement.setLong(1, id);
        ResultSet resultset = queryStatement.executeQuery();
        Producer producer = null;
        if(resultset.next())
        {
            producer = new Producer(resultset.getString("name"));
            producer.setId(resultset.getLong("id"));
        }
        queryStatement.clearParameters();
        resultset.close();
        queryStatement.close();
        return producer;
    }

    public static Producer getProducerByName(Connection connection, String name) throws Exception
    {
        PreparedStatement queryStatement = connection.prepareStatement("select * from producer where name=?");
        queryStatement.setString(1, name);
        ResultSet resultset = queryStatement.executeQuery();
        Producer producer = null;
        if(resultset.next())
        {
            producer = new Producer(resultset.getString("name"));
            producer.setId(resultset.getLong("id"));
        }
        queryStatement.clearParameters();
        resultset.close();
        queryStatement.close();
        return producer;
    }
}
