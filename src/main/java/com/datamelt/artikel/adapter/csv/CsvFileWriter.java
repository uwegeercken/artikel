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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
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
    public void printProductStickers(List<ProductSticker> productStickers, int quantity) throws Exception
    {
        File csvOutputFile = new File(configuration.getSparkJava().getTempFolder() + "/" + Constants.PRODUCT_STICKERS_CSV_FILENAME);

        CsvSchema schema = CsvSchema.builder().setUseHeader(false)
                .addColumn("name")
                .addColumn("number")
                .addColumn("ingredients")
                .addColumn("allergenes")
                .addColumn("price")
                .addColumn("pricePerKilo")
                .addColumn("weight")
                .addColumn("expirationDate")
                .addColumn("dateOfPacking")

                .build();
        schema = schema.withColumnSeparator('\t');
        CsvMapper mapper = new CsvMapper();
        mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        ObjectWriter writer = mapper.writerFor(ProductSticker.class).with(schema);
        writer.writeValues(csvOutputFile).writeAll(productStickers);
        printProductStickers(quantity);
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

    private void printProductStickers(int quantity) throws Exception
    {
        if(configuration.getLabels().existBinary() && configuration.getSparkJava().existTempFolder() && configuration.getLabels().existProductStickersFile())
        {
            String inputFilename = configuration.getSparkJava().getTempFolder() + "/" + Constants.PRODUCT_STICKERS_CSV_FILENAME;
            String outputFilename = configuration.getLabels().getPdfOutputFolder() + "/" + Constants.PRODUCT_STICKERS_FILE_CONTENT_DISPOSITION_VALUE_FILENAME_PART1 + Constants.PRODUCT_STICKERS_FILE_CONTENT_DISPOSITION_VALUE_FILENAME_PART2;

            String command = configuration.getLabels().getGlabelsBinary() + " -i " + inputFilename + " -c " + quantity + " -o " + outputFilename + " " + configuration.getLabels().getProductStickersFile();
            Process process = Runtime.getRuntime().exec(command);
            //process.waitFor(15, TimeUnit.SECONDS);
            process.waitFor();

            Thread.sleep(5000);

            String printCommand = "lpr -o page-ranges=1-999"  + " -P " + configuration.getLabels().getProductStickersPrinterName() + " " + outputFilename;
            logger.info("sending stickers from [{}] to printer [{}], quantity [{}]", outputFilename, configuration.getLabels().getProductStickersPrinterName(), quantity);

            Process printProcess = Runtime.getRuntime().exec(printCommand);
            //printProcess.waitFor(60, TimeUnit.SECONDS);
            printProcess.waitFor();


//            ProcessBuilder builderScript = new ProcessBuilder();
//            builderScript.command("/home/uwe/development/artikel/glabels/printstickers.sh",configuration.getLabels().getProductStickersPrinterName(), ""+quantity);
//            Process runScript = builderScript.start();
//            runScript.waitFor(15, TimeUnit.SECONDS);
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
