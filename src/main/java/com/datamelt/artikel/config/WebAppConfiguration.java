package com.datamelt.artikel.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WebAppConfiguration
{
    private int chartingNumberOfWeeksToDisplay;

    private final int allProductsNumberOfDaysMin = 0;
    private final int allProductsNumberOfDaysMax = 9999;
    private int recentlyUnchangedProductsNumberOfDaysMin;
    private int recentlyUnchangedProductsNumberOfDaysMax;
    private int shorttermUnchangedProductsNumberOfDaysMin;
    private int shorttermUnchangedProductsNumberOfDaysMax;
    private int longtermUnchangedProductsNumberOfDaysMin;

    public int getChartingNumberOfWeeksToDisplay()
    {
        return chartingNumberOfWeeksToDisplay;
    }

    public int getRecentlyUnchangedProductsNumberOfDaysMin()
    {
        return recentlyUnchangedProductsNumberOfDaysMin;
    }

    public int getRecentlyUnchangedProductsNumberOfDaysMax() { return recentlyUnchangedProductsNumberOfDaysMax; }

    public int getShorttermUnchangedProductsNumberOfDaysMax() { return shorttermUnchangedProductsNumberOfDaysMax; }

    public int getShorttermUnchangedProductsNumberOfDaysMin() { return shorttermUnchangedProductsNumberOfDaysMin; }

    public int getLongtermUnchangedProductsNumberOfDaysMin() { return longtermUnchangedProductsNumberOfDaysMin; }

    public int getAllProductsNumberOfDaysMin() { return allProductsNumberOfDaysMin; }

    public int getAllProductsNumberOfDaysMax() { return allProductsNumberOfDaysMax; }
}
