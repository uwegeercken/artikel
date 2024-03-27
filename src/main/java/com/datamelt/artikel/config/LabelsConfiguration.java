package com.datamelt.artikel.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.File;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LabelsConfiguration
{
    private String glabelsBinary;
    private String productLabelsFile;
    private String productStickersFile;
    private String productStickersPrinterName;
    private String pdfOutputFolder;

    public String getProductLabelsFile()
    {
        return productLabelsFile;
    }

    public String getProductStickersFile()
    {
        return productStickersFile;
    }

    public String getProductStickersPrinterName() { return productStickersPrinterName; }

    public String getGlabelsBinary()
    {
        return glabelsBinary;
    }

    public boolean existProductLabelsFile()
    {
        File labelsFile = new File(productLabelsFile);
        return labelsFile.exists() && labelsFile.canRead();
    }

    public boolean existProductStickersFile()
    {
        File stickersFile = new File(productStickersFile);
        return stickersFile.exists() && stickersFile.canRead();
    }

    public boolean existBinary()
    {
        File binary = new File(glabelsBinary);
        return binary.exists() && binary.canExecute();
    }

    public String getPdfOutputFolder()
    {
        return pdfOutputFolder;
    }
}
