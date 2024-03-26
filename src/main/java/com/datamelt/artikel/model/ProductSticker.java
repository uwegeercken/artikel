package com.datamelt.artikel.model;

import com.datamelt.artikel.port.MessageBundleInterface;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ProductSticker
{
    private String name;
    private String number;
    private String ingredients;
    private String allergenes;
    private String weight;
    private String price;
    private String pricePerKilo;
    private String expirationDate;
    private String dateOfPacking;

    private static final DecimalFormat formatPrice = new DecimalFormat("#.00");
    private static final DecimalFormat formatWeight = new DecimalFormat("#");
    private static final DateTimeFormatter formatDates = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final MessageBundleInterface messages;

    public ProductSticker(Product product, int expirationDateOffset, int dateOfPackingOffset, MessageBundleInterface messages)
    {
        this.messages = messages;
        mapProductToLabel(product, expirationDateOffset, dateOfPackingOffset);
    }

    private void mapProductToLabel(Product product, int expirationDateOffset, int dateOfPackingOffset)
    {
        this.name = product.getTitle() + " " + product.getName();
        this.number = product.getNumber();
        this.ingredients = product.getIngredients();
        this.allergenes = product.getAllergenes();
        this.price =  formatPrice.format(product.getPrice()) +" €";
        this.expirationDate = LocalDate.now().plusDays(expirationDateOffset).format(formatDates);
        this.dateOfPacking = LocalDate.now().plusDays(dateOfPackingOffset).format(formatDates);
        if(product.getWeight()>0)
        {
            this.pricePerKilo =  formatPrice.format(getPricePerKilo(product.getWeight(), product.getPrice())) + " €/kg";
            if(product.getWeight()<1)
            {
                this.weight = formatWeight.format(product.getWeight() * 1000) + " " + messages.get("LABELS_UNIT_TYPE_WEIGHT_SMALL");
            }
            else
            {
                this.weight = formatWeight.format(product.getWeight()) + " " + messages.get("LABELS_UNIT_TYPE_WEIGHT_LARGE");
            }
        }
        else
        {
            this.weight = product.getQuantity() + " " + messages.get("LABELS_UNIT_TYPE_PIECE");
        }
    }

    private double getPricePerKilo(double weight, double price)
    {
        return (1000 / (weight * 1000)) * price;
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

    public String getExpirationDate() { return expirationDate; }

    public String getDateOfPacking() { return dateOfPacking; }

    public void setIngredients(String ingredients) { this.ingredients = ingredients; }

    public String getAllergenes() { return allergenes; }

    public void setAllergenes(String allergenes) { this.allergenes = allergenes; }

    public String getWeight()
    {
        return weight;
    }

    public String getPricePerKilo() { return pricePerKilo; }

    public void setWeight(String weight)
    {
        this.weight = weight;
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }

    public void setExpirationDate(String expirationDate) { this.expirationDate = expirationDate; }

    public void setDateOfPacking(String dateOfPacking) { this.dateOfPacking = dateOfPacking; }

    public void setPricePerKilo(String pricePerKilo) { this.pricePerKilo = pricePerKilo; }
}
