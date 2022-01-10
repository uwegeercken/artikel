package com.datamelt.utilities;

import java.util.Calendar;
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

    public static long getUnixTimestampValidUntil(int year, int month, int day)
    {
        Date gueltigBis = new Calendar.Builder()
                .set(Calendar.YEAR,year)
                .set(Calendar.MONTH,month)
                .set(Calendar.DAY_OF_MONTH,day)
                .set(Calendar.HOUR_OF_DAY,23)
                .set(Calendar.MINUTE,59)
                .set(Calendar.SECOND,59)
                .build()
                .getTime();
        return gueltigBis.getTime();
    }

    public static long getUnixTimestampValidFrom(int year, int month, int day)
    {
        Date gueltigVon = new Calendar.Builder()
                .set(Calendar.YEAR,year)
                .set(Calendar.MONTH,month)
                .set(Calendar.DAY_OF_MONTH,day)
                .set(Calendar.HOUR_OF_DAY,0)
                .set(Calendar.MINUTE,0)
                .set(Calendar.SECOND,0)
                .build()
                .getTime();
        return gueltigVon.getTime();
    }
}
