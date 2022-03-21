package com.datamelt.artikel.model;

import com.datamelt.artikel.util.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order
{
    private int number;
    private Date date;
    private List<ExternalProduct> items = new ArrayList<>();

    private SimpleDateFormat sdf = new SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT);

    public Order(int number, String date)
    {
        this.number = number;
        try
        {
            this.date = sdf.parse(date);
        }
        catch (ParseException ex)
        {
            ex.printStackTrace();
        }
    }

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public List<ExternalProduct> getItems()
    {
        return items;
    }

    public void addItem(ExternalProduct product)
    {
        items.add(product);
    }

    public int getNumberOfItems()
    {
        return items.size();
    }

    public double getTotalWeight()
    {
        double totalWeight = 0;
        for(ExternalProduct product : items)
        {
            totalWeight = totalWeight + product.getWeight();
        }
        return totalWeight;
    }

    public double getTotalNumberOfItemst()
    {
        int totalItems = 0;
        for(ExternalProduct product : items)
        {
            totalItems = totalItems + product.getNumberOfItems();
        }
        return totalItems;
    }

    public double getTotalPrice()
    {
        double totalPrice = 0;
        for(ExternalProduct product : items)
        {
            totalPrice = totalPrice + product.getPrice();
        }
        return totalPrice;
    }

}
