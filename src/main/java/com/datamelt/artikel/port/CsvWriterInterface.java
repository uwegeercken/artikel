package com.datamelt.artikel.port;

import com.datamelt.artikel.model.ProductLabel;
import com.datamelt.artikel.model.ProductSticker;

import java.util.List;

public interface CsvWriterInterface
{
    byte[] getProductLabelsOutputFile(List<ProductLabel> productLabels) throws Exception;
    void printProductStickers(List<ProductSticker> productStickers, int quantity) throws Exception;
}
