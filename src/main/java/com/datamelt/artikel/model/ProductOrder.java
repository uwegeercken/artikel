package com.datamelt.artikel.model;

import com.datamelt.artikel.util.Constants;

import java.text.SimpleDateFormat;
import java.util.*;

public class ProductOrder
{
    private long id;
    private String number;
    private long timestamp;
    private Map<Long, ProductOrderItem> orderItems = new HashMap<>();

    private SimpleDateFormat formatter = new SimpleDateFormat(Constants.GERMAN_DATE_FORMAT);

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public long getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(long timestamp)
    {
        this.timestamp = timestamp;
    }

    public Map<Long, ProductOrderItem> getOrderItems()
    {
        return orderItems;
    }

    public ProductOrderItem getOrderItem(long productId)
    {
        return orderItems.get(productId);
    }

    public void addOrderItem(ProductOrderItem item)
    {
        orderItems.put(item.getProduct().getId(), item);
    }

    public void removeOrderItem(ProductOrderItem item)
    {
        orderItems.remove(item.getProduct().getId());
    }

    public void setOrderItems(Map<Long, ProductOrderItem> orderItems)
    {
        this.orderItems = orderItems;
    }

    public int getTotalProductOrderItemAmount()
    {
        int totalAmount = 0;
        for(ProductOrderItem item : orderItems.values())
        {
            totalAmount = totalAmount + item.getAmount();
        }
        return totalAmount;
    }
    public String getProductOrderDate()
    {
        return formatter.format(new Date(timestamp));
    }
}
