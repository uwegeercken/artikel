package com.datamelt.artikel.config;

public class WebAppConfiguration
{
    private int chartingNumberOfWeeksToDisplay;

    private int recentlyChangedProductsNumberOfDays;
    private int shorttermChangedProductsNumberOfDays;
    private int longtermChangedProductsNumberOfDays;

    public int getChartingNumberOfWeeksToDisplay()
    {
        return chartingNumberOfWeeksToDisplay;
    }

    public int getRecentlyChangedProductsNumberOfDays()
    {
        return recentlyChangedProductsNumberOfDays;
    }

    public int getShorttermChangedProductsNumberOfDays()
    {
        return shorttermChangedProductsNumberOfDays;
    }

    public int getLongtermChangedProductsNumberOfDays()
    {
        return longtermChangedProductsNumberOfDays;
    }
}
