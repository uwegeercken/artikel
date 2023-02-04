package com.datamelt.artikel.config;

public class WebAppConfiguration
{
    private int chartingNumberOfWeeksToDisplay;

    private final int allProductsNumberOfDays = 0;
    private int recentlyUnchangedProductsNumberOfDays;
    private int shorttermUnchangedProductsNumberOfDays;
    private int longtermUnchangedProductsNumberOfDays;

    public int getChartingNumberOfWeeksToDisplay()
    {
        return chartingNumberOfWeeksToDisplay;
    }

    public int getRecentlyUnchangedProductsNumberOfDays()
    {
        return recentlyUnchangedProductsNumberOfDays;
    }

    public int getShorttermUnchangedProductsNumberOfDays()
    {
        return shorttermUnchangedProductsNumberOfDays;
    }

    public int getLongtermUnchangedProductsNumberOfDays()
    {
        return longtermUnchangedProductsNumberOfDays;
    }

    public int getAllProductsNumberOfDays()
    {
        return allProductsNumberOfDays;
    }
}
