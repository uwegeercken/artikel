package com.datamelt.artikel.adapter.database.sqlite;

import com.datamelt.artikel.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class UserUpdate
{
    private static final String SQL_INSERT = "insert into user (name, full_name, password, type) values(?,?,?,?)";
    private static final String SQL_UPDATE = "update user set name=?, full_name=?, password=?, type=? where id=?";
    private static final String SQL_DELETE = "delete from user where id=?";

    private Connection connection;

    public UserUpdate(Connection connection)
    {
        this.connection = connection;
    }

    void addUser(User user)
    {
        try
        {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, user.getName());
            statement.setString(2, user.getFullName());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole());
            statement.executeUpdate();
            statement.clearParameters();

            ResultSet resultset = statement.getGeneratedKeys();
            resultset.next();
            user.setId(resultset.getLong(1));

            resultset.close();
            statement.close();

        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    void updateUser(User user)
    {
        try
        {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, user.getName());
            statement.setString(2, user.getFullName());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole());
            statement.setLong(5, user.getId());
            statement.executeUpdate();
            statement.clearParameters();

            statement.close();

        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    void removeUser(long id)
    {
        try
        {
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
            statement.setLong(1, id);
            statement.executeUpdate();
            statement.clearParameters();

            statement.close();

        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }
}
