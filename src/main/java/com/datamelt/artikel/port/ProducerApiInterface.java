package com.datamelt.artikel.port;

import com.datamelt.artikel.adapter.web.form.Form;
import com.datamelt.artikel.model.Producer;

import java.util.List;

public interface ProducerApiInterface
{
    List<Producer> getAllProducers() throws Exception;
    Producer getProducerById(long id) throws Exception;
    void updateProducer(long id, Form form) throws Exception;
    void addProducer(Form form) throws Exception;
    boolean getIsUniqueProducer(long id, String name) throws Exception;
    void deleteProducer(long id) throws Exception;
}
