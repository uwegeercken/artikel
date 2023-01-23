package com.datamelt.artikel.model.highcharts;

import java.util.ArrayList;
import java.util.HashMap;

public class SeriesCollection
{
    private ArrayList<Serie> series = new ArrayList<>();

    public void add(Serie serie)
    {
        series.add(serie);
    }

    public ArrayList<Serie> getSeries()
    {
        return series;
    }
}
