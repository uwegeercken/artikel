package com.datamelt.artikel.adapter.database.sqlite;

import com.datamelt.artikel.config.DatabaseConfiguration;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

class SqliteConnection
{
    static Connection getConnection(DatabaseConfiguration configuration) throws Exception {
        Connection connection = null;
        try {
            File databaseFile = new File(configuration.getName());
            boolean existDatabase = databaseFile.exists();
            Class.forName(configuration.getJdbcClass());
            // if db does not exist, the file is created by the driver
            connection = DriverManager.getConnection(configuration.getConnection() + ":" + configuration.getName());
            if(!existDatabase)
            {
                SqliteTable.createTables(connection);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return connection;
    }
}
