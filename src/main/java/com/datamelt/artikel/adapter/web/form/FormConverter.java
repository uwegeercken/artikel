package com.datamelt.artikel.adapter.web.form;

import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.Product;
import com.datamelt.artikel.model.ProductContainer;

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

    public static Form convertProductContainer(ProductContainer container)
    {
        Form form = new Form();
        form.put(FormField.ID, String.valueOf(container.getId()));
        form.put(FormField.NAME, container.getName());
        return form;
    }
}
