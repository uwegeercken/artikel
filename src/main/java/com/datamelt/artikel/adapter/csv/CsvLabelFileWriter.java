package com.datamelt.artikel.adapter.csv;

import com.datamelt.artikel.config.LabelsConfiguration;
import com.datamelt.artikel.model.ProductLabel;
import com.datamelt.artikel.port.CsvWriterInterface;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.util.List;

public class CsvLabelFileWriter implements CsvWriterInterface
{
    private final LabelsConfiguration configuration;

    public CsvLabelFileWriter(LabelsConfiguration configuration)
    {
        this.configuration = configuration;
    }

    @Override
    public void writeLabelsCsvFile(List<ProductLabel> productLabels) throws Exception
    {
        File csvOutputFile = new File(configuration.getCsvOutputFilename());

        CsvSchema schema = CsvSchema.builder().setUseHeader(false)
                .addColumn("title")
                .addColumn("name")
                .addColumn("subtitle")
                .addColumn("unit")
                .addColumn("price")
                .build();
        schema.withColumnSeparator('\t');
        CsvMapper mapper = new CsvMapper();
        mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        ObjectWriter writer = mapper.writerFor(ProductLabel.class).with(schema);

        writer.writeValues(csvOutputFile).writeAll(productLabels);

    }

}
