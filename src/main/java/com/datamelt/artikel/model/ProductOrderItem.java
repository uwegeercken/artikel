package com.datamelt.artikel.model;

public class ProductOrderItem
{
    private long id;
    private ProductOrder productOrder;
    private Product product;
    private long timestamp;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public ProductOrder getProductOrder()
    {
        return productOrder;
    }

    public void setProductOrder(ProductOrder productOrder)
    {
        this.productOrder = productOrder;
    }

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

    public long getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(long timestamp)
    {
        this.timestamp = timestamp;
    }
}
