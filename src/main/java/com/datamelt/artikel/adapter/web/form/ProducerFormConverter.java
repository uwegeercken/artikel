package com.datamelt.artikel.adapter.web.form;

import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.Product;

public class ProducerFormConverter
{
    public static ProducerForm convertProducer(Producer producer)
    {
        ProducerForm form = new ProducerForm();
        form.put(ProducerFormField.ID, String.valueOf(producer.getId()));
        form.put(ProducerFormField.NAME, producer.getName());
        form.put(ProducerFormField.NO_ORDERING, String.valueOf(producer.getNoOrdering()));
        return form;
    }

    public static Producer convertProducer(ProducerForm form)
    {
        Producer producer = new Producer(form.get(ProducerFormField.NAME));
        producer.setId(Long.parseLong(form.get(ProducerFormField.ID)));
        producer.setNoOrdering(Integer.parseInt(form.get(ProducerFormField.NO_ORDERING)));
        return producer;
    }
}
