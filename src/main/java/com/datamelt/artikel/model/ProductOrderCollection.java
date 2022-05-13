package com.datamelt.artikel.model;

import java.util.HashMap;
import java.util.Map;

public class ProductOrderCollection
{
    private Map<Long, ProductOrder> orders = new HashMap<>();

    public ProductOrder get(long producerId)
    {
        return orders.get(producerId);
    }

    public void add(ProductOrder order)
    {
        orders.put(order.getProducerId(),order);
    }

    public void remove(long producerId)
    {
        orders.remove(producerId);
    }

    public int size()
    {
        return orders.size();
    }

    public int size(long producerId)
    {
        return orders.get(producerId).getOrderItems().size();
    }
}
