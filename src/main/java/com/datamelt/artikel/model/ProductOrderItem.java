package com.datamelt.artikel.model;

public class ProductOrderItem
{
    private long id;
    private long productOrderId;
    private long productId;
    private int amount;
    private long timestamp;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public int getAmount()
    {
        return amount;
    }

    public void setAmount(int amount)
    {
        this.amount = amount;
    }

    public long getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(long timestamp)
    {
        this.timestamp = timestamp;
    }

    public long getProductOrderId()
    {
        return productOrderId;
    }

    public void setProductOrderId(long productOrderId)
    {
        this.productOrderId = productOrderId;
    }

    public long getProductId()
    {
        return productId;
    }

    public void setProductId(long productId)
    {
        this.productId = productId;
    }

    public void increaseAmount()
    {
        amount = amount +1;
    }

    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof ProductOrderItem)) {
            return false;
        }
        else
        {
            ProductOrderItem item = (ProductOrderItem) object;
            return item.getProductId() == this.productId;
        }
    }

}
