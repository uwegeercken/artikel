package com.datamelt.artikel.adapter.web.form;

import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.Product;
import com.datamelt.artikel.model.ProductContainer;
import com.datamelt.artikel.model.ProductOrigin;

public class FormConverter
{
    public static Form convertToForm(Producer producer)
    {
        Form form = new Form();
        form.put(FormField.ID, String.valueOf(producer.getId()));
        form.put(FormField.NAME, producer.getName());
        form.put(FormField.NO_ORDERING, String.valueOf(producer.getNoOrdering()));
        return form;
    }

    public static Form convertToForm(ProductContainer container)
    {
        Form form = new Form();
        form.put(FormField.ID, String.valueOf(container.getId()));
        form.put(FormField.NAME, container.getName());
        return form;
    }

    public static Form convertToForm(ProductOrigin origin)
    {
        Form form = new Form();
        form.put(FormField.ID, String.valueOf(origin.getId()));
        form.put(FormField.NAME, origin.getName());
        return form;
    }

    public static Form convertToForm(Product product)
    {
        Form form = new Form();
        form.put(FormField.ID, String.valueOf(product.getId()));
        form.put(FormField.PRODUCT_NAME, product.getName());
        form.put(FormField.NUMBER, product.getNumber());
        form.put(FormField.TITLE, product.getTitle());
        form.put(FormField.SUBTITLE, product.getSubtitle());
        form.put(FormField.QUANTITY, String.valueOf(product.getQuantity()));
        form.put(FormField.WEIGHT,String.valueOf(product.getWeight()));
        form.put(FormField.PRICE,String.valueOf(product.getPrice()));
        form.put(FormField.PRODUCER_ID,String.valueOf(product.getProducer().getId()));
        form.put(FormField.CONTAINER_ID,String.valueOf(product.getContainer().getId()));
        form.put(FormField.ORIGIN_ID,String.valueOf(product.getOrigin().getId()));
        return form;
    }
}
