package com.datamelt.artikel.port;

import com.datamelt.artikel.adapter.web.form.ProducerForm;
import com.datamelt.artikel.adapter.web.form.ProductForm;
import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.Product;

import java.util.List;

public interface ProducerApiInterface
{
    List<Producer> getAllProducers() throws Exception;
    Producer getProducerById(long id) throws Exception;
    void updateProducer(long id, ProducerForm form) throws Exception;
    void addProducer(ProducerForm form) throws Exception;
    boolean getIsUniqueProducer(long id, String name) throws Exception;
}
