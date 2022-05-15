package com.datamelt.artikel.model;

public class ProductLabel
{
    private String title;
    private String name;
    private String subtitle;
    private String unit;
    private String price;

    public ProductLabel(Product product)
    {
        mapProductToLabel(product);
    }

    private void mapProductToLabel(Product product)
    {
        this.title = product.getTitle();
        this.name = product.getName();
        this.subtitle = product.getSubtitle();
        if(product.getWeight()>0)
        {
            this.unit = product.getWeight() + " Kg";
        }
        else
        {
            this.unit = product.getQuantity() + " " + product.getContainer().getName();
        }
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSubtitle()
    {
        return subtitle;
    }

    public void setSubtitle(String subtitle)
    {
        this.subtitle = subtitle;
    }

    public String getUnit()
    {
        return unit;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }
}
