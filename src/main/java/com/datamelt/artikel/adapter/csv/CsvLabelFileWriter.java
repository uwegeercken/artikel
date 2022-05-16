package com.datamelt.artikel.adapter.csv;

import com.datamelt.artikel.config.LabelsConfiguration;
import com.datamelt.artikel.model.ProductLabel;
import com.datamelt.artikel.port.CsvWriterInterface;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

public class CsvLabelFileWriter implements CsvWriterInterface
{
    private static final String csvOutputFilename = "labels.csv";
    private static final String pdfOutputFilename = "etiketten.pdf";

    private final LabelsConfiguration configuration;

    public CsvLabelFileWriter(LabelsConfiguration configuration)
    {
        this.configuration = configuration;
    }

    @Override
    public byte[] getLabelsOutputFile(List<ProductLabel> productLabels) throws Exception
    {
        File csvOutputFile = new File(configuration.getTempFolder() + "/" + csvOutputFilename);

        CsvSchema schema = CsvSchema.builder().setUseHeader(false)
            .addColumn("title")
            .addColumn("name")
            .addColumn("subtitle")
            .addColumn("unit")
            .addColumn("price")
            .build();
        schema = schema.withColumnSeparator('\t');
        CsvMapper mapper = new CsvMapper();
        mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        ObjectWriter writer = mapper.writerFor(ProductLabel.class).with(schema);
        writer.writeValues(csvOutputFile).writeAll(productLabels);

        return writeLabelsOutputFile(csvOutputFile);
    }

    private byte[] writeLabelsOutputFile(File csvOutputFile) throws Exception
    {
        String inputFilename = configuration.getTempFolder() + "/" + csvOutputFilename;
        String outputFilename = configuration.getTempFolder() + "/" + pdfOutputFilename;

        String command = configuration.getGlabelsBinary() + " -i " + inputFilename + " -o " + outputFilename + " " + configuration.getGlabelsFile();
        Process process = Runtime.getRuntime().exec(command);
        File file = new File(outputFilename);
        FileInputStream stream = new FileInputStream(file);
        return stream.readAllBytes();
    }

}
