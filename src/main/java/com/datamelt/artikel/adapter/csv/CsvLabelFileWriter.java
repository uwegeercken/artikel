package com.datamelt.artikel.adapter.csv;

import com.datamelt.artikel.config.LabelsConfiguration;
import com.datamelt.artikel.port.LabelFileInterface;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;

public class CsvLabelFileWriter implements LabelFileInterface
{
    private final LabelsConfiguration configuration;

    public CsvLabelFileWriter(LabelsConfiguration configuration)
    {
        this.configuration = configuration;
    }

    @Override
    public void writeLabelsCsvFile(long producerId)
    {
        File csvOutputFile = new File(configuration.getCsvOutputFilename());

        CsvSchema schema = CsvSchema.builder().setUseHeader(false)
                .addColumn("title")
                .addColumn("name")
                .addColumn("subtitle")
                .addColumn("unit")
                .addColumn("price")
                .build();


    }

}
