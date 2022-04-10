package com.datamelt.artikel.adapter.database.sqlite;

import java.sql.Connection;

public class SqliteTable
{
    private static final String CREATE_TABLE_MARKET ="CREATE TABLE \"market\" (" +
            "\"id\"INTEGER NOT NULL," +
            "\"name\"TEXT NOT NULL UNIQUE," +
            "\"type\"TEXT NOT NULL," +
            "PRIMARY KEY(\"id\" AUTOINCREMENT)" +
            ")";

    private static final String CREATE_TABLE_PRODUCER = "CREATE TABLE \"producer\" (" +
            "\"id\"INTEGER NOT NULL," +
            "\"name\"TEXT NOT NULL UNIQUE," +
            "\"no_ordering\"INTEGER DEFAULT 0," +
            "PRIMARY KEY(\"id\" AUTOINCREMENT)" +
            ")";

    private static final String CREATE_TABLE_PRODUCT ="CREATE TABLE \"product\" (" +
            "\"id\"INTEGER NOT NULL," +
            "\"number\"TEXT NOT NULL UNIQUE," +
            "\"name\"TEXT NOT NULL," +
            "\"description\"TEXT," +
            "\"quantity\"INTEGER NOT NULL DEFAULT 1," +
            "\"weight\"NUMERIC NOT NULL DEFAULT 0," +
            "\"price\"NUMERIC NOT NULL DEFAULT 0," +
            "\"producer_id\"INTEGER NOT NULL," +
            "\"productcontainer_id\"INTEGER," +
            "\"productorigin_id\"INTEGER," +
            "\"timestamp\"INTEGER," +
            "PRIMARY KEY(\"id\" AUTOINCREMENT)" +
            ")";

    private static final String CREATE_TABLE_PRODUCTCONTAINER = "CREATE TABLE \"productcontainer\" (" +
            "\"id\"INTEGER NOT NULL," +
            "\"name\"TEXT NOT NULL UNIQUE," +
            "PRIMARY KEY(\"id\" AUTOINCREMENT)" +
            ")";

    private static final String CREATE_TABLE_PRODUCTORDER = "CREATE TABLE \"productorder\" (" +
            "\"id\"INTEGER NOT NULL," +
            "\"number\"TEXT NOT NULL UNIQUE," +
            "\"timestamp\"INTEGER NOT NULL," +
            "PRIMARY KEY(\"id\" AUTOINCREMENT)" +
            ")";

    private static final String CREATE_TABLE_PRODUCTORDER_ITEM = "CREATE TABLE \"productorder_item\" (" +
            "\"id\"INTEGER NOT NULL," +
            "\"productorder_id\"INTEGER NOT NULL," +
            "\"product_id\"INTEGER NOT NULL," +
            "PRIMARY KEY(\"id\" AUTOINCREMENT)" +
            ")";

    private static final String CREATE_TABLE_PRODUCTORIGIN = "CREATE TABLE \"productorigin\" (" +
            "\"id\"INTEGER NOT NULL," +
            "\"name\"TEXT NOT NULL UNIQUE," +
            "PRIMARY KEY(\"id\" AUTOINCREMENT)" +
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
    }
}
