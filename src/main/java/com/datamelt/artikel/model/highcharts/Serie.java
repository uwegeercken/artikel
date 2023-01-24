package com.datamelt.artikel.model.highcharts;

import java.util.ArrayList;

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
        //String valuesAsString = values.toString().replaceAll("0.0", "'nil'");

        return values.toString();
    }

    public String getName()
    {
        return name;
    }
}
