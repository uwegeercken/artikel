package com.datamelt.artikel.adapter.csv;

import com.datamelt.artikel.app.web.WebApplication;
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

import java.io.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
        mapper
            .configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true)
            .writerFor(ProductLabel.class).with(schema)
            .writeValues(csvOutputFile).writeAll(productLabels);

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
        mapper
            .configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true)
            .writerFor(ProductSticker.class).with(schema)
            .writeValues(csvOutputFile).writeAll(productStickers);

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

    private void printProductStickersBackup(int quantity) throws Exception
    {
        if(configuration.getLabels().existBinary() && configuration.getSparkJava().existTempFolder() && configuration.getLabels().existProductStickersFile())
        {
            String inputFilename = configuration.getSparkJava().getTempFolder() + "/" + Constants.PRODUCT_STICKERS_CSV_FILENAME;
            String outputFilename = configuration.getLabels().getPdfOutputFolder() + "/" + Constants.PRODUCT_STICKERS_FILE_FILENAME_PART1 + "_" + System.currentTimeMillis() + Constants.PRODUCT_STICKERS_FILE_FILENAME_PART2;

            String command = configuration.getLabels().getGlabelsBinary() + " -i " + inputFilename + " -c " + quantity + " -o " + outputFilename + " " + configuration.getLabels().getProductStickersFile();
            Process process = Runtime.getRuntime().exec(new String[]{"bash", "-c", command});
            //process.waitFor(15, TimeUnit.SECONDS);
            process.waitFor();

            String printCommand = "lpr -o page-ranges=1-" + quantity + " -P " + configuration.getLabels().getProductStickersPrinterName() + " " + outputFilename;
            logger.info("sending stickers from [{}] to printer [{}], quantity [{}]", outputFilename, configuration.getLabels().getProductStickersPrinterName(), quantity);

            Process printProcess = Runtime.getRuntime().exec(new String[]{"bash", "-c", printCommand});
            //printProcess.waitFor(60, TimeUnit.SECONDS);
            printProcess.onExit().thenRun(() ->
                {
                    int exitCode = process.exitValue();
                    logger.error("error printing stickers. exit code [{}]", exitCode);
                }
            );
            process.waitFor();
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

    private void printProductStickers(int quantity) throws Exception
    {
        if(configuration.getLabels().existBinary() && configuration.getSparkJava().existTempFolder() && configuration.getLabels().existProductStickersFile())
        {
            String inputFilename = configuration.getSparkJava().getTempFolder() + "/" + Constants.PRODUCT_STICKERS_CSV_FILENAME;
            String outputFilename = configuration.getLabels().getPdfOutputFolder() + "/" + Constants.PRODUCT_STICKERS_FILE_FILENAME_PART1 + "_" + System.currentTimeMillis() + Constants.PRODUCT_STICKERS_FILE_FILENAME_PART2;

            String command = configuration.getLabels().getGlabelsBinary() + " -i " + inputFilename + " -c " + quantity + " -o " + outputFilename + " " + configuration.getLabels().getProductStickersFile();
            String printCommand = "lpr -o page-ranges=1-" + quantity + " -P " + configuration.getLabels().getProductStickersPrinterName() + " " + outputFilename;
            logger.info("sending stickers from [{}] to printer [{}], quantity [{}]", outputFilename, configuration.getLabels().getProductStickersPrinterName(), quantity);

            try
            {
                if(checkPrinterAvailability())
                {
                    if (executeCommand("create pdf", command))
                    {
                        // If the PDF creation was successful, execute the print command asynchronously
                        new Thread(() -> {
                            try
                            {
                                executeCommand("print labels", printCommand);
                            }
                            catch (IOException e)
                            {
                                logger.error("error executing print command: [{}]", e.getMessage());
                            }
                        }).start();
                    }
                    else
                    {
                        throw new Exception("error creating pdf file for printing");
                    }
                }
                else
                {
                    throw new Exception(WebApplication.getMessages().get("PRODUCTSTICKERS_FORM_PRINT_ERROR_PRINTER_NOT_FOUND"));
                }
            }
            catch (IOException e)
            {
                logger.error("error executing pdf create command [{}]", e.getMessage());
            }
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

    private boolean executeCommand(String commandDescription, String command) throws IOException
    {
        Process process = Runtime.getRuntime().exec(new String[]{"bash", "-c", command});

        // Capture the output of the command
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream())))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                logger.debug("command [{}] output: {}" , commandDescription, line);
            }
        }
        try
        {
            boolean finished = process.waitFor(60, TimeUnit.SECONDS);
            if (!finished) {
                logger.debug("command [{}] timed out. terminating process...",commandDescription);
                process.destroy(); // Terminate the process if it exceeds the timeout
                return false;
            }
            int exitCode = process.exitValue();
            logger.debug("command [{}] executed with exit code [{}]", commandDescription, + exitCode);
            return exitCode == 0; // Return true if the command was successful
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt(); // Restore interrupted status
            throw new IOException(WebApplication.getMessages().get("PRODUCTSTICKERS_FORM_PRINT_ERROR_PROCESS_INTERRUPTED"));
        }
    }

    private boolean checkPrinterAvailability()
    {
        String command = "lpstat -p";
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"bash", "-c", command});
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            // Check each line of output for the printer name
            while ((line = reader.readLine()) != null)
            {
                if (line.contains(configuration.getLabels().getProductStickersPrinterName()))
                {
                    return true;
                }
            }
        }
        catch (IOException e)
        {
            logger.error(WebApplication.getMessages().get("PRODUCTSTICKERS_FORM_PRINT_ERROR_PRINTER_NOT_FOUND"));
        }
        return false; // Printer not found
    }
}
