package com.datamelt.artikel.model;

import java.util.*;

public class ProductOrder
{
    private long id;
    private String number;
    private long timestamp;
    private Map<Long, ProductOrderItem> orderItems = new HashMap<>();

    public ProductOrder(String number, long timestamp)
    {
        this.number = number;
        this.timestamp = timestamp;
    }

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
        orderItems.put(item.getProductId(), item);
    }

    public void setOrderItems(Map<Long, ProductOrderItem> orderItems)
    {
        this.orderItems = orderItems;
    }
}
