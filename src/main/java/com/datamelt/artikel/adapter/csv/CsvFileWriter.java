package com.datamelt.artikel.adapter.csv;

import com.datamelt.artikel.config.MainConfiguration;
import com.datamelt.artikel.model.ProductLabel;
import com.datamelt.artikel.model.ProductSticker;
import com.datamelt.artikel.port.CsvWriterInterface;
import com.datamelt.artikel.util.Constants;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CsvFileWriter implements CsvWriterInterface
{
    private static final Logger logger = LoggerFactory.getLogger(CsvFileWriter.class);
    private final MainConfiguration configuration;

    public CsvFileWriter(MainConfiguration configuration)
    {
        this.configuration = configuration;
    }

    @Override
    public byte[] getProductLabelsOutputFile(List<ProductLabel> productLabels) throws Exception
    {
        File csvOutputFile = new File(configuration.getSparkJava().getTempFolder() + "/" + Constants.PRODUCT_LABELS_CSV_FILENAME);

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
        return writeProductLabelsOutputFile(csvOutputFile);
    }

    @Override
    public void printProductStickers(List<ProductSticker> productStickers) throws Exception
    {
        File csvOutputFile = new File(configuration.getSparkJava().getTempFolder() + "/" + Constants.PRODUCT_STICKERS_CSV_FILENAME);

        CsvSchema schema = CsvSchema.builder().setUseHeader(false)
                .addColumn("name")
                .addColumn("number")
                .addColumn("ingredients")
                .addColumn("allergenes")
                .addColumn("unit")
                .addColumn("price")
                .build();
        schema = schema.withColumnSeparator('\t');
        CsvMapper mapper = new CsvMapper();
        mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        ObjectWriter writer = mapper.writerFor(ProductSticker.class).with(schema);
        writer.writeValues(csvOutputFile).writeAll(productStickers);
        printProductStickers(csvOutputFile);
    }

    private byte[] writeProductLabelsOutputFile(File csvOutputFile) throws Exception
    {
        if(configuration.getLabels().existBinary() && configuration.getSparkJava().existTempFolder() && configuration.getLabels().existProductLabelsFile())
        {
            String inputFilename = configuration.getSparkJava().getTempFolder() + "/" + Constants.PRODUCT_LABELS_CSV_FILENAME;
            String outputFilename = configuration.getLabels().getPdfOutputFolder() + "/" + Constants.PRODUCT_LABELS_FILE_CONTENT_DISPOSITION_VALUE_FILENAME_PART1 + Constants.PRODUCT_LABELS_FILE_CONTENT_DISPOSITION_VALUE_FILENAME_PART2;

            String command = configuration.getLabels().getGlabelsBinary() + " -i " + inputFilename + " -o " + outputFilename + " " + configuration.getLabels().getProductLabelsFile();
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor(15, TimeUnit.SECONDS);
            File file = new File(outputFilename);
            FileInputStream stream = new FileInputStream(file);
            return stream.readAllBytes();
        }
        else
        {
            if(!configuration.getLabels().existBinary())
            {
                logger.error("configuration item: glabelsBinary [{}] does not exist or can not be executed", configuration.getLabels().getGlabelsBinary());
            }
            else if(!configuration.getSparkJava().existTempFolder())
            {
                logger.error("configuration item: tempFolder [{}] does not exist or can not be accessed", configuration.getSparkJava().getTempFolder());
            }
            else if(!configuration.getLabels().existProductLabelsFile())
            {
                logger.error("configuration item: productLabelsFile [{}] does not exist or can not be read", configuration.getLabels().existProductLabelsFile());
            }
            return null;
        }
    }

    private void printProductStickers(File csvOutputFile) throws Exception
    {
        if(configuration.getLabels().existBinary() && configuration.getSparkJava().existTempFolder() && configuration.getLabels().existProductStickersFile())
        {
            String inputFilename = configuration.getSparkJava().getTempFolder() + "/" + Constants.PRODUCT_STICKERS_CSV_FILENAME;
            String outputFilename = configuration.getLabels().getPdfOutputFolder() + "/" + Constants.PRODUCT_STICKERS_FILE_CONTENT_DISPOSITION_VALUE_FILENAME_PART1 + Constants.PRODUCT_STICKERS_FILE_CONTENT_DISPOSITION_VALUE_FILENAME_PART2;

            String command = configuration.getLabels().getGlabelsBinary() + " -i " + inputFilename + " -o " + outputFilename + " " + configuration.getLabels().getProductStickersFile();
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor(15, TimeUnit.SECONDS);

            String printCommand = "lpr -P M110s -#1 " + outputFilename;
            Process printProcess = Runtime.getRuntime().exec(printCommand);
            printProcess.waitFor(30, TimeUnit.SECONDS);

//            File file = new File(outputFilename);
//            FileInputStream stream = new FileInputStream(file);
//            return stream.readAllBytes();
        }
        else
        {
            if(!configuration.getLabels().existBinary())
            {
                logger.error("configuration item: glabelsBinary [{}] does not exist or can not be executed", configuration.getLabels().getGlabelsBinary());
            }
            else if(!configuration.getSparkJava().existTempFolder())
            {
                logger.error("configuration item: tempFolder [{}] does not exist or can not be accessed", configuration.getSparkJava().getTempFolder());
            }
            else if(!configuration.getLabels().existProductStickersFile())
            {
                logger.error("configuration item: productStickersFile [{}] does not exist or can not be read", configuration.getLabels().existProductStickersFile());
            }
        }
    }



}
