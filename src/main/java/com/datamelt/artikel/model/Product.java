package com.datamelt.artikel.model;

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
    private final ProductContainer container;
    private final ProductOrigin origin;

    private Product(ProductBuilder builder) {
        this.id = builder.id;
        this.number = builder.number;
        this.name = builder.name;
        this.description = builder.description;
        this.quantity = builder.quantity;
        this.weight = builder.weight;
        this.price = builder.price;
        this.producer = builder.producer;
        this.container = builder.container;
        this.origin = builder.origin;
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
        private ProductContainer container;
        private ProductOrigin origin;

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

        public ProductBuilder container(ProductContainer container)
        {
            this.container = container;
            return this;
        }

        public ProductBuilder origin(ProductOrigin origin)
        {
            this.origin = origin;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
