package com.datamelt.artikel.model;

import com.datamelt.artikel.util.Constants;

import java.text.SimpleDateFormat;
import java.util.*;

public class ProductOrder
{
    private long id;
    private String number;
    private long producerId;
    private Producer producer;
    private long timestampCreatedDate;
    private long timestampOrderDate;
    private long timestampEmailSent;
    private boolean shopLabelsOnly = false;
    private Map<Long, ProductOrderItem> orderItems = new HashMap<>();
    private SimpleDateFormat formatterCreatedDate = new SimpleDateFormat(Constants.GERMAN_DATE_FORMAT_WITH_WEEKDAY);
    private SimpleDateFormat formatterOrderDate = new SimpleDateFormat(Constants.GERMAN_DATE_FORMAT_WITH_WEEKDAY_NO_TIME);

    public ProductOrder()
    {
    }

    public ProductOrder(long producerId)
    {
        this.producerId = producerId;
    }

    public ProductOrder(long producerId, boolean shopLabelsOnly)
    {
        this.producerId = producerId;
        this.shopLabelsOnly = shopLabelsOnly;
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

    public long getProducerId()
    {
        return producerId;
    }

    public void setProducerId(long producerId)
    {
        this.producerId = producerId;
    }

    public long getTimestampCreatedDate() { return timestampCreatedDate; }

    public void setTimestampCreatedDate(long timestampCreatedDate) { this.timestampCreatedDate = timestampCreatedDate; }

    public long getTimestampOrderDate() { return timestampOrderDate; }

    public long getTimestampEmailSent() { return timestampEmailSent; }

    public void setTimestampEmailSent(long timestampEmailSent) { this.timestampEmailSent = timestampEmailSent; }

    public void setTimestampOrderDate(long timestampOrderDate) { this.timestampOrderDate = timestampOrderDate; }

    public Producer getProducer() { return producer; }

    public void setProducer(Producer producer) { this.producer = producer; }

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
    public String getProductCreatedDate()
    {
        return formatterCreatedDate.format(new Date(timestampCreatedDate));
    }

    public String getProductOrderDate() { return formatterOrderDate.format(new Date(timestampOrderDate)); }

    public String getProductOrderEmailDate() { return formatterOrderDate.format(new Date(timestampEmailSent)); }

    public List<Product> getProducts()
    {
        List<Product> products = new ArrayList<>();
        for(ProductOrderItem item : orderItems.values())
        {
            products.add(item.getProduct());
        }
        return products;
    }

    public boolean getShopLabelsOnly()
    {
        return shopLabelsOnly;
    }


}
