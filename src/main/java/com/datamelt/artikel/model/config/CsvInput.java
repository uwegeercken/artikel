package com.datamelt.artikel.model.config;

public class CsvInput
{
    private String productsFilename;
    private String producersFilename;
    private String marketsFilename;
    private String ordersFilename;
    private String orderitemsFilename;

    public String getProductsFilename()
    {
        return productsFilename;
    }

    public String getProducersFilename()
    {
        return producersFilename;
    }

    public String getMarketsFilename()
    {
        return marketsFilename;
    }

    public String getOrdersFilename()
    {
        return ordersFilename;
    }

    public String getOrderitemsFilename() { return orderitemsFilename; }
}
