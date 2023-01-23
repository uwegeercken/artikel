package com.datamelt.artikel.model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ProductHistory
{
    private static final long PRODUCT_CHANGED_MAX_TIME = 24*60*60*1000;

    private long id;
    private long productId;
    private double price;
    private long timestamp;

    public ProductHistory(long productId) {
        this.productId = productId;
    }

    public ProductHistory(Product product)
    {
        this.productId = product.getId();
        this.price = product.getPrice();
        this.timestamp = product.getTimestamp();
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public long getProductId()
    {
        return productId;
    }

    public void setProductId(long productId)
    {
        this.productId = productId;
    }

    public long getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(long timestamp)
    {
        this.timestamp = timestamp;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public int getTimestampYear()
    {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date(timestamp));
        return calendar.get(Calendar.YEAR);
    }

    public int getTimestampWeek()
    {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date(timestamp));
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public String getTimestampYearWeek()
    {
        return getTimestampYear() + "-" + getTimestampWeek();
    }
}
