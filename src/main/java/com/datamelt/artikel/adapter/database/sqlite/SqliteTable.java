package com.datamelt.artikel.adapter.database.sqlite;

import com.datamelt.artikel.app.web.util.HashGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class SqliteTable
{
    private static final Logger logger =  LoggerFactory.getLogger(SqliteTable.class);

    private static final String CREATE_TABLE_MARKET ="CREATE TABLE if not exists \"market\" (" +
            "\"id\"INTEGER NOT NULL," +
            "\"name\"TEXT NOT NULL UNIQUE," +
            "\"type\"TEXT NOT NULL," +
            "PRIMARY KEY(\"id\" AUTOINCREMENT)" +
            ")";

    private static final String CREATE_TABLE_PRODUCER = "CREATE TABLE if not exists \"producer\" (" +
            "\"id\"INTEGER NOT NULL," +
            "\"name\"TEXT NOT NULL UNIQUE," +
            "\"email_address\"TEXT," +
            "\"no_ordering\"INTEGER DEFAULT 0," +
            "PRIMARY KEY(\"id\" AUTOINCREMENT)" +
            ")";

    private static final String CREATE_TABLE_PRODUCT ="CREATE TABLE if not exists \"product\" (" +
            "\"id\"INTEGER NOT NULL," +
            "\"number\"TEXT NOT NULL UNIQUE," +
            "\"name\"TEXT NOT NULL," +
            "\"title\"TEXT," +
            "\"subtitle\"TEXT," +
            "\"quantity\"INTEGER NOT NULL DEFAULT 1," +
            "\"weight\"NUMERIC NOT NULL DEFAULT 0," +
            "\"price\"NUMERIC NOT NULL DEFAULT 0," +
            "\"producer_id\"INTEGER NOT NULL," +
            "\"productcontainer_id\"INTEGER," +
            "\"productorigin_id\"INTEGER," +
            "\"timestamp\"INTEGER," +
            "PRIMARY KEY(\"id\" AUTOINCREMENT)" +
            ")";

    private static final String CREATE_TABLE_PRODUCTCONTAINER = "CREATE TABLE if not exists \"productcontainer\" (" +
            "\"id\"INTEGER NOT NULL," +
            "\"name\"TEXT NOT NULL UNIQUE," +
            "PRIMARY KEY(\"id\" AUTOINCREMENT)" +
            ")";

    private static final String CREATE_TABLE_PRODUCTORDER = "CREATE TABLE if not exists \"productorder\" (" +
            "\"id\"INTEGER NOT NULL," +
            "\"number\"TEXT NOT NULL UNIQUE," +
            "\"producer_id\"INTEGER NOT NULL," +
            "\"timestamp_order_date\"INTEGER NOT NULL," +
            "\"timestamp_created_date\"INTEGER NOT NULL," +
            "\"timestamp_email_sent\"INTEGER," +
            "PRIMARY KEY(\"id\" AUTOINCREMENT)" +
            ")";

    private static final String CREATE_TABLE_PRODUCTORDER_ITEM = "CREATE TABLE if not exists \"productorder_item\" (" +
            "\"id\"INTEGER NOT NULL," +
            "\"productorder_id\"INTEGER NOT NULL," +
            "\"product_id\"INTEGER NOT NULL," +
            "\"timestamp\"INTEGER NOT NULL," +
            "PRIMARY KEY(\"id\" AUTOINCREMENT)" +
            ")";

    private static final String CREATE_TABLE_PRODUCTORIGIN = "CREATE TABLE if not exists \"productorigin\" (" +
            "\"id\"INTEGER NOT NULL," +
            "\"name\"TEXT NOT NULL UNIQUE," +
            "PRIMARY KEY(\"id\" AUTOINCREMENT)" +
            ")";

    private static final String CREATE_TABLE_USER = "CREATE TABLE if not exists \"user\" (" +
                "\"id\"INTEGER NOT NULL UNIQUE," +
                "\"name\"\tTEXT NOT NULL UNIQUE," +
                "\"full_name\"\tTEXT," +
                "\"password\"\tTEXT," +
                "\"type\"\tTEXT," +
                "PRIMARY KEY(\"id\")" +
                ")";

    private static final String CREATE_USER_ADMIN = "insert into user (name, full_name, password, type) values (?,?,?,?)";

    public static void createTables(Connection connection) throws Exception
    {
        logger.info("creating table [user]");
        connection.createStatement().execute(CREATE_TABLE_USER);
        logger.info("creating table [market]");
        connection.createStatement().execute(CREATE_TABLE_MARKET);
        logger.info("creating table [producer]");
        connection.createStatement().execute(CREATE_TABLE_PRODUCER);
        logger.info("creating table [productcontainer]");
        connection.createStatement().execute(CREATE_TABLE_PRODUCTCONTAINER);
        logger.info("creating table [productorigin]");
        connection.createStatement().execute(CREATE_TABLE_PRODUCTORIGIN);
        logger.info("creating table [product]");
        connection.createStatement().execute(CREATE_TABLE_PRODUCT);
        logger.info("creating table [productorder]");
        connection.createStatement().execute(CREATE_TABLE_PRODUCTORDER);
        logger.info("creating table [productorder_item]");
        connection.createStatement().execute(CREATE_TABLE_PRODUCTORDER_ITEM);
    }

    public static void createAdminUser(Connection connection)
    {
        logger.info("creating user [admin]");
        String password = HashGenerator.generatePassword();
        String hash = HashGenerator.generate(password);
        logger.info("administrative user [{}]", "admin");
        logger.info("administrative user password [{}]", password);
        try
        {
            PreparedStatement statement = connection.prepareStatement(CREATE_USER_ADMIN);
            statement.setString(1, "admin");
            statement.setString(2, "Administrator");
            statement.setString(3, hash);
            statement.setString(4, "admin");
            statement.executeUpdate();
            statement.clearParameters();
        }
        catch(Exception ex)
        {
            logger.error("error creating admin user [{}]", ex.getMessage());
        }
    }
}
