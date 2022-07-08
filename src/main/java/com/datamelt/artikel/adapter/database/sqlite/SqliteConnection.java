package com.datamelt.artikel.adapter.database.sqlite;

import com.datamelt.artikel.app.web.WebApplication;
import com.datamelt.artikel.config.DatabaseConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

class SqliteConnection
{
    private static final Logger logger =  LoggerFactory.getLogger(SqliteConnection.class);

    static Connection getConnection(DatabaseConfiguration configuration) throws Exception {
        Connection connection = null;
        try {
            logger.info("using database file: [{}]",configuration.getName());
            File databaseFile = new File(configuration.getName());
            boolean existDatabase = databaseFile.exists();
            Class.forName(configuration.getJdbcClass());
            // if db does not exist, the file is created by the driver
            connection = DriverManager.getConnection(configuration.getConnection() + ":" + configuration.getName());
            if(!existDatabase)
            {
                logger.warn("database not found - creating database and table structure: [{}]", configuration.getName());
                SqliteTable.createTables(connection);
                SqliteTable.createAdminUser(connection);
            }
        }
        catch (Exception ex)
        {
            logger.error("exception trying to connect to database: [{}]",ex.getMessage());
        }
        return connection;
    }
}
