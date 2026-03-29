package com.datamelt.artikel.adapter.printer;

import com.datamelt.artikel.adapter.csv.CsvFileWriter;
import com.datamelt.artikel.config.MainConfiguration;
import com.datamelt.artikel.port.PrinterInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class LabelsPrinter implements PrinterInterface
{
    private static final Logger logger = LoggerFactory.getLogger(LabelsPrinter.class);
    private final MainConfiguration configuration;

    public LabelsPrinter(MainConfiguration configuration)
    {
        this.configuration = configuration;
    }

    @Override
    public void emptyPrinterQueue() throws Exception
    {
        String userName = System.getProperty("user.name");
        ProcessBuilder pb = new ProcessBuilder("cancel", "-a", "-u", userName,  configuration.getLabels().getProductStickersPrinterName());
        pb.redirectErrorStream(true);
        Process process = pb.start();

        String output = new String(process.getInputStream().readAllBytes());
        int exitCode = process.waitFor();

        if (exitCode == 0)
        {
            logger.info("emptying queue successful for printer [{}], user [{}]", configuration.getLabels().getProductStickersPrinterName(), userName);
        }
        else
        {
            logger.error("emptying queue failed for printer [{}], user [{}], exit code [{}]: {}", configuration.getLabels().getProductStickersPrinterName(), userName, exitCode, output);
        }
    }
}
