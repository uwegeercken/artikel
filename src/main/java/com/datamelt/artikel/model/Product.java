package com.datamelt.artikel.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.datamelt.artikel.util.Constants.GERMAN_DATE_ONLY_FORMAT;

public class Product
{
    private static final long PRODUCT_CHANGED_MAX_TIME = 24*60*60*1000;

    private long id;
    private String number;
    private String name;
    private String title;
    private String subtitle;
    private int quantity;
    private double weight;
    private double price;
    private int unavailable;
    private String ingredients;
    private String allergenes;
    private Producer producer;
    private ProductContainer container;
    private ProductOrigin origin;
    private long timestamp;

    public Product(String number) {
        this.number = number;
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getNumber()
    {
        return number;
    }

    public String getName()
    {
        return name;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public double getWeight() { return weight; }

    public double getPrice() { return price; }

    public String getIngredients() { return ingredients; }

    public String getAllergenes() { return allergenes; }

    public Producer getProducer() { return producer; }

    public ProductContainer getContainer() { return container; }

    public ProductOrigin getOrigin() { return origin; }

    public int getUnavailable() { return unavailable; }

    public void setUnavailable(int unavailable) { this.unavailable = unavailable; }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public void setWeight(double weight)
    {
        this.weight = weight;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public void setIngredients(String ingredients) { this.ingredients = ingredients; }

    public void setAllergenes(String allergenes) { this.allergenes = allergenes; }

    public void setProducer(Producer producer)
    {
        this.producer = producer;
    }

    public void setContainer(ProductContainer container)
    {
        this.container = container;
    }

    public void setOrigin(ProductOrigin origin)
    {
        this.origin = origin;
    }

    public long getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(long timestamp)
    {
        this.timestamp = timestamp;
    }

    public String getLastChangedDate()
    {
        SimpleDateFormat sdf = new SimpleDateFormat(GERMAN_DATE_ONLY_FORMAT);
        return sdf.format(new Date(timestamp));
    }

    public boolean recentlyChanged()
    {
        Date now = new Date();
        Date lastChanged = new Date(timestamp);
        if(now.getTime() - lastChanged.getTime() <= PRODUCT_CHANGED_MAX_TIME)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getSubtitle() { return subtitle; }

    public void setSubtitle(String subtitle) { this.subtitle = subtitle; }
}
