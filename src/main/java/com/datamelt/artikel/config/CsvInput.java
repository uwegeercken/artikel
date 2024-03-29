package com.datamelt.artikel.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CsvInput
{
    private String filesFolder;
    private String productsFilename;
    private String producersFilename;
    private String marketsFilename;
    private String ordersFilename;
    private String orderitemsFilename;
    private String productContainersFilename;
    private String productOriginsFilename;
    private String usersFilename;

    public String getFilesFolder() { return filesFolder; }

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

    public String getProductContainersFilename() { return productContainersFilename; }

    public String getProductOriginsFilename() { return productOriginsFilename; }

    public String getUsersFilename() { return usersFilename; }
}
