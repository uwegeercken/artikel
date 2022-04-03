package com.datamelt.artikel.adapter.web;

import com.datamelt.artikel.model.Product;

public class ProductConverter
{
    public static ProductForm convertProduct(Product product)
    {
        ProductForm form = new ProductForm();
        form.put(ProductFormField.NAME, product.getName());
        form.put(ProductFormField.NUMBER, product.getNumber());
        form.put(ProductFormField.DESCRIPTION, product.getDescription());
        form.put(ProductFormField.QUANTITY, String.valueOf(product.getQuantity()));
        form.put(ProductFormField.WEIGHT,String.valueOf(product.getWeight()));
        form.put(ProductFormField.PRICE,String.valueOf(product.getPrice()));
        return form;
    }
}
