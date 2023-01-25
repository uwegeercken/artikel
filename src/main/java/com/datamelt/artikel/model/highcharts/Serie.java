package com.datamelt.artikel.model.highcharts;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

public class Serie
{
    String name;
    private ArrayList<Double> values = new ArrayList<>();

    public Serie(String name)
    {
        this.name = name;
    }

    public void add(double value)
    {
        values.add(value);
    }

    public void add(double[] seriesValues)
    {
        for (int i = 0; i < seriesValues.length; i++)
        {
            values.add(seriesValues[i]);
        }
    }

    public String getValues()
    {

        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        for(int i=0; i<values.size();i++)
        {
            double value = values.get(i);
            if(value==0)
            {
                buffer.append("'nil'");
            }
            else
            {
                buffer.append(new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH)).format(value));
            }
            if(i<values.size()-1)
            {
                buffer.append(",");
            }
        }
        buffer.append("]");
        return buffer.toString();
    }

    public String getName()
    {
        return name;
    }
}
