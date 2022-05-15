package com.datamelt.artikel.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class ProductLabel
{
    private String title;
    private String name;
    private String subtitle;
    private String unit;
    private String price;

    private static final DecimalFormat formatPrice = new DecimalFormat("#.00");
    private static final DecimalFormat formatWeight = new DecimalFormat("#");

    public ProductLabel(Product product)
    {
        mapProductToLabel(product);
    }

    private void mapProductToLabel(Product product)
    {
        this.title = product.getTitle();
        this.name = product.getName();
        this.subtitle = product.getSubtitle();
        this.price =  formatPrice.format(product.getPrice());
        if(product.getWeight()>0)
        {
            if(product.getWeight()<1)
            {
                this.unit = formatWeight.format(product.getWeight() * 1000) + " g";
            }
            else
            {
                this.unit = formatWeight.format(product.getWeight()) + " Kg";
            }
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
