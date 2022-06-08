package com.datamelt.artikel.app.web.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberFormatter
{
    private final DecimalFormat formatter;

    public NumberFormatter(String locale)
    {
        Locale customLocale = new Locale(locale, locale.toUpperCase(Locale.ROOT));
        formatter = new DecimalFormat("0.00",DecimalFormatSymbols.getInstance(customLocale));
    }

    public String convertToLocale(double number)
    {
        return formatter.format(number);
    }

    public double convertToDouble(String number)
    {
        return Double.parseDouble(number.replaceAll(",", "."));
    }
}
