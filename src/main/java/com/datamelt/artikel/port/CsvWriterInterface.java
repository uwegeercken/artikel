package com.datamelt.artikel.port;

import com.datamelt.artikel.model.ProductLabel;

import java.util.List;

public interface CsvWriterInterface
{
    byte[] getProductLabelsOutputFile(List<ProductLabel> productLabels) throws Exception;
}
