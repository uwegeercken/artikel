package com.datamelt.artikel.adapter.database.sqlite;

import com.datamelt.artikel.config.DatabaseConfiguration;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

class SqliteConnection
{
    static Connection getConnection(DatabaseConfiguration configuration) throws Exception {
        File databaseFile = new File(configuration.getName());
        if(databaseFile.isFile()) {
            try {
                Class.forName(configuration.getJdbcClass());
                return DriverManager.getConnection(configuration.getConnection() + ":" + configuration.getName());
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
        else
        {
            throw new Exception("database not found: " + configuration.getName());
        }
    }
}
