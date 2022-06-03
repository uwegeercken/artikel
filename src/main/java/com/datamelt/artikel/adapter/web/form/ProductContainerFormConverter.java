package com.datamelt.artikel.adapter.web.form;

import com.datamelt.artikel.model.ProductContainer;

public class ProductContainerFormConverter
{
    public static ProductContainerForm convertProductContainer(ProductContainer productContainer)
    {
        ProductContainerForm form = new ProductContainerForm();
        form.put(ProductContainerFormField.ID, String.valueOf(productContainer.getId()));
        form.put(ProductContainerFormField.NAME, productContainer.getName());
        return form;
    }

    public static ProductContainer convertProductContainer(ProductContainerForm form)
    {
        ProductContainer productContainer = new ProductContainer(form.get(ProductContainerFormField.NAME));
        productContainer.setId(Long.parseLong(form.get(ProductContainerFormField.ID)));
        return productContainer;
    }
}
