package com.datamelt.artikel.adapter.database.sqlite;

import com.datamelt.artikel.app.web.util.HashGenerator;
import com.datamelt.artikel.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

class UserSearch
{

    private static final String SQL_QUERY_BY_ID = "select * from user where id=?";
    private static final String SQL_QUERY_BY_NAME = "select * from user where lower(name)=lower(?)";
    private static final String SQL_QUERY_EXISTS = "select count(1) as counter from user where name=?";
    private static final String SQL_QUERY_IS_UNIQUE = "select count(1) as counter from user where name=? and id!=?";

    static User getUserById(Connection connection, long id) throws Exception
    {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_BY_ID);
        statement.setLong(1, id);
        ResultSet resultset = statement.executeQuery();
        User user = null;
        if(resultset.next())
        {
            user = new User(resultset.getString("name"));
            user.setId(resultset.getLong("id"));
            user.setFullName(resultset.getString("full_name"));
            user.setPassword(resultset.getString("password"));
        }
        statement.clearParameters();
        resultset.close();
        statement.close();
        return user;
    }

    static User getUserByName(Connection connection, String name) throws Exception
    {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_BY_NAME);
        statement.setString(1, name);
        ResultSet resultset = statement.executeQuery();
        User user = null;
        if(resultset.next())
        {
            user = new User(resultset.getString("name"));
            user.setId(resultset.getLong("id"));
            user.setFullName(resultset.getString("full_name"));
            user.setPassword(resultset.getString("password"));
        }
        statement.clearParameters();
        resultset.close();
        statement.close();
        return user;
    }

    static boolean getExistUser(Connection connection, String name) throws Exception
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

    public static boolean getIsUniqueUser(Connection connection, long id, String name) throws Exception
    {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY_IS_UNIQUE);
        statement.setString(1, name);
        statement.setLong(2, id);
        ResultSet resultset = statement.executeQuery();
        boolean isUnique = false;
        if(resultset.next())
        {
            isUnique = resultset.getLong("counter") == 1;
        }
        statement.clearParameters();
        resultset.close();
        statement.close();
        return isUnique;
    }
}
