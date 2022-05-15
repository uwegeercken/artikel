package com.datamelt.artikel.port;

import com.datamelt.artikel.model.ProductLabel;

import java.util.List;

public interface CsvWriterInterface
{
    void writeLabelsCsvFile(List<ProductLabel> productLabels) throws Exception;
}
