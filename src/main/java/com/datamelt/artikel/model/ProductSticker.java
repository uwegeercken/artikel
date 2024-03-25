package com.datamelt.artikel.model;

import com.datamelt.artikel.port.MessageBundleInterface;

import java.text.DecimalFormat;

public class ProductSticker
{
    private String name;
    private String number;
    private String ingredients;
    private String allergenes;
    private String unit;
    private String price;

    private static final DecimalFormat formatPrice = new DecimalFormat("#.00");
    private static final DecimalFormat formatWeight = new DecimalFormat("#");

    private MessageBundleInterface messages;

    public ProductSticker(Product product, MessageBundleInterface messages)
    {
        this.messages = messages;
        mapProductToLabel(product);
    }

    private void mapProductToLabel(Product product)
    {
        this.name = product.getTitle() + " " + product.getName();
        this.number = product.getNumber();
        this.ingredients = product.getIngredients();
        this.allergenes = product.getAllergenes();
        this.price =  formatPrice.format(product.getPrice());
        if(product.getWeight()>0)
        {
            if(product.getWeight()<1)
            {
                this.unit = formatWeight.format(product.getWeight() * 1000) + " " + messages.get("LABELS_UNIT_TYPE_WEIGHT_SMALL");
            }
            else
            {
                this.unit = formatWeight.format(product.getWeight()) + " " + messages.get("LABELS_UNIT_TYPE_WEIGHT_LARGE");
            }
        }
        else
        {
            this.unit = product.getQuantity() + " " + messages.get("LABELS_UNIT_TYPE_PIECE");
        }
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getNumber() { return number; }

    public void setNumber(String number) { this.number = number; }

    public String getIngredients() { return ingredients; }

    public void setIngredients(String ingredients) { this.ingredients = ingredients; }

    public String getAllergenes() { return allergenes; }

    public void setAllergenes(String allergenes) { this.allergenes = allergenes; }

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
