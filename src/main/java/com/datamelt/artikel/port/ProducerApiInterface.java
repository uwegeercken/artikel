package com.datamelt.artikel.port;

import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.Product;

import java.util.List;

public interface ProducerApiInterface
{
    List<Producer> getAllProducers() throws Exception;
}
