package com.datamelt.utilities;

import java.util.Date;

public class DateUtility
{
    public static Date convertFromLong(long date)
    {
        return new Date(date);
    }

    public static long convertFromDate(Date date)
    {
        return date.getTime();
    }
}
