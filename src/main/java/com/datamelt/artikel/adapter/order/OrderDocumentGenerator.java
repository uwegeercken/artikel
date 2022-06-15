package com.datamelt.artikel.adapter.order;

import com.datamelt.artikel.config.MainConfiguration;
import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.Product;
import com.datamelt.artikel.model.ProductOrder;
import com.datamelt.artikel.model.ProductOrderItem;
import com.datamelt.artikel.port.OrderDocumentInterface;
import com.datamelt.artikel.util.FileUtility;
import org.apache.commons.lang.CharEncoding;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.asciidoctor.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;



public class OrderDocumentGenerator implements OrderDocumentInterface
{
    private static final Logger logger = LoggerFactory.getLogger(OrderDocumentGenerator.class);
    private final MainConfiguration configuration;

    private static final String DATE_TIME_FORMAT = "yyyyMMddHHmmss";
    public static final String ORDER_DATE_FORMAT = "dd.MM.yyyy";
    private static final DecimalFormat formatOrderNumber = new DecimalFormat("0000");
    private static final String ORDER_FILENAME_PREFIX = "order_";
    private static final String ASCIIDOC_FILENAME_EXTENSION = ".adoc";
    private static final String PDF_FILENAME_EXTENSION = ".pdf";

    private final SimpleDateFormat orderDocumentDateFormat = new SimpleDateFormat(ORDER_DATE_FORMAT);
    private final Date creationDate;

    public OrderDocumentGenerator(MainConfiguration configuration)
    {
        this.configuration = configuration;
        this.creationDate = new Date();
    }

    @Override
    public byte[] getOrderDocument(Producer producer, ProductOrder order, List<Product> products)
    {
        String templateFilename = ORDER_FILENAME_PREFIX + order.getProducerId() + ASCIIDOC_FILENAME_EXTENSION;
        String asciiDocument = getOrderTemplate(configuration.getAsciidoc().getTemplateFileFolder(), templateFilename, order, products);

        generateDocument(producer, order, asciiDocument);
        return getDocument(producer, order);
    }

    @Override
    public String getOrderDocumentFilename(Producer producer, ProductOrder order)
    {
        return ORDER_FILENAME_PREFIX + formatOrderNumber.format(order.getId()) + PDF_FILENAME_EXTENSION;
    }

    private String getOrderTemplate(String folder, String filename, ProductOrder order, List<Product> products)
    {
        List<ProductOrderItem> allProductsOrderItems = new ArrayList<>();
        for(Product product : products)
        {
            ProductOrderItem item = new ProductOrderItem();
            item.setProduct(product);

            for(Map.Entry<Long,ProductOrderItem> entry : order.getOrderItems().entrySet())
            {
                if(entry.getValue().getProduct().getId() == product.getId())
                {
                    item.setAmount(entry.getValue().getAmount());
                }
            }
            allProductsOrderItems.add(item);
        }

        Properties velocityProperties = new Properties();
        velocityProperties.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
        velocityProperties.put("file.resource.loader.path", folder);
        VelocityEngine engine = new VelocityEngine(velocityProperties);
        Context context = new VelocityContext();
        context.put("productorderitems", allProductsOrderItems);
        context.put("createddate", orderDocumentDateFormat.format(order.getTimestampCreatedDate()));
        context.put("orderdate", orderDocumentDateFormat.format(order.getTimestampOrderDate()));
        StringWriter writer = new StringWriter();

        boolean success = engine.mergeTemplate(filename, CharEncoding.UTF_8, context, writer );
        if(success)
        {
            return writer.toString();
        }
        else
        {
            return null;
        }
    }

    private void generateDocument(Producer producer, ProductOrder order, String asciiDocument)
    {
        System.setProperty("jruby.compat.version", "RUBY1_9");
        System.setProperty("jruby.compile.mode", "OFF");

        String pdfFilename = FileUtility.getFullFilename(configuration.getAsciidoc().getPdfOutputFolder(), getOrderDocumentFilename(producer, order));

         Attributes attributes = Attributes.builder()
                .attribute("pdf-theme", configuration.getAsciidoc().getThemeFile())
                //.attribute("pdf-fontsdir", "path_to_fonts")
                //.icons("font")
                .build();

        Options options = Options.builder()
            .inPlace(true)
            .safe(SafeMode.UNSAFE)
            .attributes(attributes)
            .toFile(new File(pdfFilename))
            .backend("pdf")
            .build();

        try (Asciidoctor asciidoctor = Asciidoctor.Factory.create())
        {
            asciidoctor.convert(asciiDocument, options);
        }
    }

    private byte[] getDocument(Producer producer, ProductOrder order)
    {
        byte[] output =null;
        try
        {
            File file = new File(FileUtility.getFullFilename(configuration.getAsciidoc().getPdfOutputFolder(), getOrderDocumentFilename(producer, order)));
            FileInputStream stream = new FileInputStream(file);
            output = stream.readAllBytes();
        }
        catch (Exception ex)
        {

        }
        return output;
    }

}
