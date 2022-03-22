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
    private String number;
    private long timestamp;
    private List<Product> items = new ArrayList<>();

    public Order(String number, long timestamp)
    {
        this.number = number;
        this.timestamp = timestamp;
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public long getTimestamp() { return timestamp; }

    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public List<Product> getItems()
    {
        return items;
    }

    public void addItem(Product product)
    {
        items.add(product);
    }

    public void setItems(List<Product> items) { this.items = items; }

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
