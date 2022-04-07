package com.datamelt.artikel.adapter.web.form;

import com.datamelt.artikel.model.Product;

public class ProductFormConverter
{
    public static ProductForm convertProduct(Product product)
    {
        ProductForm form = new ProductForm();
        form.put(ProductFormField.ID, String.valueOf(product.getId()));
        form.put(ProductFormField.NAME, product.getName());
        form.put(ProductFormField.NUMBER, product.getNumber());
        form.put(ProductFormField.DESCRIPTION, product.getDescription());
        form.put(ProductFormField.QUANTITY, String.valueOf(product.getQuantity()));
        form.put(ProductFormField.WEIGHT,String.valueOf(product.getWeight()));
        form.put(ProductFormField.PRICE,String.valueOf(product.getPrice()));
        form.put(ProductFormField.PRODUCER_ID,String.valueOf(product.getProducer().getId()));
        form.put(ProductFormField.CONTAINER_ID,String.valueOf(product.getContainer().getId()));
        form.put(ProductFormField.ORIGIN_ID,String.valueOf(product.getOrigin().getId()));
        return form;
    }

    public static Product convertProduct(ProductForm form)
    {
        Product product = new Product(form.get(ProductFormField.NUMBER));
        product.setId(Long.parseLong(form.get(ProductFormField.ID)));
        product.setName(form.get(ProductFormField.NAME));
        product.setDescription(form.get(ProductFormField.DESCRIPTION));
        product.setQuantity(Integer.parseInt(form.get(ProductFormField.QUANTITY)));
        product.setWeight(Double.parseDouble(form.get(ProductFormField.WEIGHT)));
        product.setPrice(Double.parseDouble(form.get(ProductFormField.PRICE)));
        product.getProducer().setId(Long.parseLong(form.get(ProductFormField.PRODUCER_ID)));
        product.getContainer().setId(Long.parseLong(form.get(ProductFormField.CONTAINER_ID)));
        product.getOrigin().setId(Long.parseLong(form.get(ProductFormField.ORIGIN_ID)));

        return product;
    }
}
