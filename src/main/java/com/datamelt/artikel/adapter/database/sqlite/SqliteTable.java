package com.datamelt.artikel.adapter.database.sqlite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

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
            "\"timestamp\"INTEGER NOT NULL," +
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
            "\"password\"\tTEXT," +
            "\"full_name\"\tTEXT," +
            "\"type\"\tTEXT," +
            "PRIMARY KEY(\"id\")" +
            ")";

    public static void createTables(Connection connection) throws Exception
    {
        connection.createStatement().execute(CREATE_TABLE_MARKET);
        connection.createStatement().execute(CREATE_TABLE_PRODUCER);
        connection.createStatement().execute(CREATE_TABLE_PRODUCTCONTAINER);
        connection.createStatement().execute(CREATE_TABLE_PRODUCTORIGIN);
        connection.createStatement().execute(CREATE_TABLE_PRODUCT);
        connection.createStatement().execute(CREATE_TABLE_PRODUCTORDER);
        connection.createStatement().execute(CREATE_TABLE_PRODUCTORDER_ITEM);
        connection.createStatement().execute(CREATE_TABLE_MARKET);
    }
}
