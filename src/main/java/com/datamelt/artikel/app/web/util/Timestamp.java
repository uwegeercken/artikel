package com.datamelt.artikel.app.web.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Timestamp
{
    private static SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    public static String convertFromUnixTimestamp(long unixTimestamp)
    {
        return formatter.format(new Date(unixTimestamp));
    }

    public static long convertFromDateString(String dateString) throws Exception
    {
        return formatter.parse(dateString).getTime();
    }
}
