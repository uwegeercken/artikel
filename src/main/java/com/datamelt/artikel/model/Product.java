package com.datamelt.artikel.model;

public class Product
{
    private long id;
    private String number;
    private String name;
    private String description;
    private int quantity;
    private double weight;
    private double price;
    private Producer producer;
    private ProductContainer container;
    private ProductOrigin origin;

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

    public String getDescription()
    {
        return description;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public double getWeight() { return weight; }

    public double getPrice() { return price; }

    public Producer getProducer() { return producer; }

    public ProductContainer getContainer() { return container; }

    public ProductOrigin getOrigin() { return origin; }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setDescription(String description)
    {
        this.description = description;
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
}
