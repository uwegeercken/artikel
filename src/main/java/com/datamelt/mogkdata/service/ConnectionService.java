package com.datamelt.mogkdata.service;

import com.datamelt.mogkdata.model.config.MainConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionService
{
    public static Connection getConnection(MainConfiguration configuration)
    {
        try
        {
            Class.forName(configuration.getDatabase().getJdbcClass());
            return DriverManager.getConnection(configuration.getDatabase().getConnection());
        } catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }
}
