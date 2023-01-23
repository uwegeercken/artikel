package com.datamelt.artikel.util;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CalendarUtility
{
    public static int getWeek(long timestamp)
    {
        Date date = new Date(timestamp);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public static int getCurrentWeek()
    {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public static int getCurrentYear()
    {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static int getLastWeek(int year)
    {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.set(year, Calendar.DECEMBER, 31);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public static String[] getWeeks(int numberOfWeeks)
    {
        String[] weeks = new String[numberOfWeeks];
        String[] weeksReversed = new String[numberOfWeeks];
        int currentWeek = getCurrentWeek();
        int currentYear = getCurrentYear();
        int previousYear =  currentYear -1;
        int lastWeekPreviousYear = getLastWeek(previousYear);
        int counter=0;
        int previousYearCounter=0;
        for(int i = currentWeek;counter<numberOfWeeks;i--)
        {
           if(currentWeek-counter>=1)
           {
               weeks[counter] = currentYear + "-" + (currentWeek - counter);
           }
           else
           {
               weeks[counter] = previousYear + "-" + (lastWeekPreviousYear - previousYearCounter);
               previousYearCounter++;
           }
           counter++;
        }
        int counterReversed = 0;
        for(int i=weeks.length-1;i>=0;i--)
        {
            weeksReversed[counterReversed] = weeks[i];
            counterReversed++;
        }
        return weeksReversed;
    }

    public static void main(String[] args)
    {

       int x =  CalendarUtility.getWeek(1663694293972l);
       int y =  CalendarUtility.getCurrentWeek();
       int z = CalendarUtility.getLastWeek(2022);
       String[] weeks = CalendarUtility.getWeeks(10);
       System.out.println("weeks: " + weeks);
    }
}
