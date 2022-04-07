package com.datamelt.artikel.adapter.database.sqlite;

import com.datamelt.artikel.config.DatabaseConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;

class SqliteConnection
{
    static Connection getConnection(DatabaseConfiguration configuration)
    {
        try
        {
            Class.forName(configuration.getJdbcClass());
            return DriverManager.getConnection(configuration.getConnection());
        } catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }
}
