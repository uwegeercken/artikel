package com.datamelt.artikel.adapter.web.validator;

import java.util.Map;

public class FormFieldValidator
{
    public static boolean validateInteger(String value)
    {
        try
        {
            Integer.parseInt(value);
            return true;
        }
        catch(Exception ex)
        {
            return false;
        }
    }

    public static boolean validateLong(String value)
    {
        try
        {
            Long.parseLong(value);
            return true;
        }
        catch(Exception ex)
        {
            return false;
        }
    }

    public static boolean validateDouble(String value)
    {
        try
        {
            Double.parseDouble(value);
            return true;
        }
        catch(Exception ex)
        {
            return false;
        }
    }
}
