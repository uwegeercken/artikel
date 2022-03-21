package com.datamelt.artikel.model;

import com.datamelt.artikel.util.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order
{
    private long id;
    private int number;
    private Date date;
    private List<Product> items = new ArrayList<>();

    public Order(int number, String date)
    {
        this.number = number;
        try
        {
            this.date = new SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT).parse(date);
        }
        catch (ParseException ex)
        {
            ex.printStackTrace();
        }
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    public Date getDate() { return date; }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public List<Product> getItems()
    {
        return items;
    }

    public void addItem(Product product)
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
        for(Product product : items)
        {
            totalWeight = totalWeight + product.getWeight();
        }
        return totalWeight;
    }

    public double getTotalPrice()
    {
        double totalPrice = 0;
        for(Product product : items)
        {
            totalPrice = totalPrice + product.getPrice();
        }
        return totalPrice;
    }

}
