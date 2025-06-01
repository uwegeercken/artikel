package com.datamelt.artikel.app.web.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberFormatter
{
    private final DecimalFormat priceFormatter;
    private final DecimalFormat weightFormatter;

    public NumberFormatter(String locale)
    {
        Locale customLocale = new Locale(locale, locale.toUpperCase(Locale.ROOT));
        priceFormatter = new DecimalFormat("0.00",DecimalFormatSymbols.getInstance(customLocale));
        weightFormatter = new DecimalFormat("0.000",DecimalFormatSymbols.getInstance(customLocale));
    }

    public String convertPriceToLocale(double number)
    {
        return priceFormatter.format(number);
    }

    public String convertWeightToLocale(double number)
    {
        return weightFormatter.format(number);
    }

    public double convertToDouble(String number)
    {
        return Double.parseDouble(number.replaceAll(",", "."));
    }
}
