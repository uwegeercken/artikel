package com.datamelt.mogkdata.model;

public class ExternalProduct
{
    private final String number;
    private final String name;
    private final String description;
    private final int quantity;
    private final int numberOfItems;
    private final double weight;
    private final double price;
    private final Producer producer;

    private ExternalProduct(ProductBuilder builder) {
        this.number = builder.number;
        this.name = builder.name;
        this.description = builder.description;
        this.quantity = builder.quantity;
        this.numberOfItems = builder.numberOfItems;
        this.weight = builder.weight;
        this.price = builder.price;
        this.producer = builder.producer;
    }

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

    public int getNumberOfItems()
    {
        return numberOfItems;
    }

    public double getWeight() { return weight; }

    public double getPrice() { return price; }

    public Producer getProducer() { return producer; }

    public static class ProductBuilder
    {
        private final String number;
        private final String name;
        private String description;
        private int quantity;
        private int numberOfItems;
        private double weight;
        private double price;
        private Producer producer;

        public ProductBuilder(String number, String name)
        {
            this.number = number;
            this.name = name;
        }

        public ProductBuilder description(String description)
        {
            this.description = description;
            return this;
        }

        public ProductBuilder numberOfItems(int numberOfItems)
        {
            this.numberOfItems = numberOfItems;
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

        public ExternalProduct build() {
            return new ExternalProduct(this);
        }
    }
}
