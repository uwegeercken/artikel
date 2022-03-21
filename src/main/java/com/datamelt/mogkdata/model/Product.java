package com.datamelt.mogkdata.model;

public class Product
{
    private long id;
    private final String number;
    private final String name;
    private final String description;
    private final int quantity;
    private final double weight;
    private final double price;
    private final Producer producer;

    private Product(ProductBuilder builder) {
        this.id = builder.id;
        this.number = builder.number;
        this.name = builder.name;
        this.description = builder.description;
        this.quantity = builder.quantity;
        this.weight = builder.weight;
        this.price = builder.price;
        this.producer = builder.producer;
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

    public static class ProductBuilder
    {
        private final String number;
        private final String name;

        private long id;
        private String description;
        private int quantity;
        private double weight;
        private double price;
        private Producer producer;

        public ProductBuilder(String number, String name, Producer producer)
        {
            this.number = number;
            this.name = name;
            this.producer = producer;
        }

        public ProductBuilder id(long id)
        {
            this.id = id;
            return this;
        }

        public ProductBuilder description(String description)
        {
            this.description = description;
            return this;
        }

        public ProductBuilder quantity(int quantity)
        {
            this.quantity = quantity;
            return this;
        }

        public ProductBuilder weight(double weight)
        {
            this.weight = weight;
            return this;
        }

        public ProductBuilder price(double price)
        {
            this.price = price;
            return this;
        }

        public ProductBuilder producer(Producer producer)
        {
            this.producer = producer;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
