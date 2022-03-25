package com.datamelt.artikel.adapter.database.sqlite;

import com.datamelt.artikel.model.config.DatabaseConfiguration;
import com.datamelt.artikel.model.config.MainConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

class SqliteConnection
{
    public static Connection getConnection(DatabaseConfiguration configuration)
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
