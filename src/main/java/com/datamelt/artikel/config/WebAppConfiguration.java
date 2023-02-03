package com.datamelt.artikel.config;

public class WebAppConfiguration
{
    private int chartingNumberOfWeeksToDisplay;

    private int allProductsNumberOfDays = 0;
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

    public int getAllProductsNumberOfDays()
    {
        return allProductsNumberOfDays;
    }
}
