package com.datamelt.artikel.adapter.order;

import com.datamelt.artikel.config.AsciidocConfiguration;
import com.datamelt.artikel.config.LabelsConfiguration;
import com.datamelt.artikel.config.MainConfiguration;
import com.datamelt.artikel.model.Producer;
import com.datamelt.artikel.model.ProductOrder;
import com.datamelt.artikel.port.OrderDocumentInterface;
import com.datamelt.artikel.util.FileUtility;
import org.apache.commons.lang.CharEncoding;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Attributes;
import org.asciidoctor.AttributesBuilder;
import org.asciidoctor.SafeMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import static org.asciidoctor.OptionsBuilder.options;

public class OrderDocumentGenerator implements OrderDocumentInterface
{
    private static final Logger logger = LoggerFactory.getLogger(OrderDocumentGenerator.class);
    private MainConfiguration configuration;

    private static final String YEAR_MONTH_DAY_FORMAT = "yyyyMMdd";
    public static final String ORDER_DATE_FORMAT = "dd.MM.yyyy";
    private static final DecimalFormat formatOrderNumber = new DecimalFormat("0000");
    private static final String ORDER_FILENAME_PREFIX = "order_";
    private static final String ASCIIDOC_FILENAME_EXTENSION = ".adoc";
    private static final String PDF_FILENAME_EXTENSION = ".pdf";

    private SimpleDateFormat orderDocumentFilenameDateFormat = new SimpleDateFormat(YEAR_MONTH_DAY_FORMAT);
    private SimpleDateFormat orderDocumentDateFormat = new SimpleDateFormat(ORDER_DATE_FORMAT);

    private Date creationDate;

    public OrderDocumentGenerator(MainConfiguration configuration)
    {
        this.configuration = configuration;
        this.creationDate = new Date();
    }

    @Override
    public byte[] getOrderDocument(Producer producer, ProductOrder order)
    {
        String templateFilename = ORDER_FILENAME_PREFIX + order.getProducerId() + ASCIIDOC_FILENAME_EXTENSION;
        String asciiDocument = getOrderTemplate(configuration.getAsciidoc().getDocumentsFolder(), templateFilename, order);

        generateDocument(producer, order, asciiDocument);
        return getDocument(producer, order);
    }

    @Override
    public String getOrderDocumentFilename(Producer producer, ProductOrder order)
    {
        return ORDER_FILENAME_PREFIX + formatOrderNumber.format(order.getId()) + "_" + producer.getName() + orderDocumentFilenameDateFormat.format(creationDate) + "_" + PDF_FILENAME_EXTENSION;
    }

    private String getOrderTemplate(String folder, String filename, ProductOrder order)
    {
        Properties velocityProperties = new Properties();
        velocityProperties.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
        velocityProperties.put("file.resource.loader.path", folder);
        VelocityEngine engine = new VelocityEngine(velocityProperties);
        Context context = new VelocityContext();
        context.put("productorderitems", order.getOrderItems());
        context.put("createddate", orderDocumentDateFormat.format(creationDate));
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
        final Asciidoctor asciidoctor = Asciidoctor.Factory.create();

        String pdfFilename = FileUtility.getFullFilename(configuration.getSparkJava().getTempFolder(), getOrderDocumentFilename(producer, order));

        Attributes attributes = AttributesBuilder.attributes()
                .attribute("pdf-theme",  FileUtility.getFullFilename(configuration.getAsciidoc().getDocumentsFolder(),configuration.getAsciidoc().getThemeFile()))
                //.attribute("pdf-fontsdir", "path_to_fonts")
                //.icons("font")
                .get();

        Map<String, Object> options = options()
                .inPlace(true)
                .safe(SafeMode.UNSAFE)
                .attributes(attributes)
                .toFile(new File( pdfFilename))
                .backend("pdf")
                .asMap();

        asciidoctor.convert(asciiDocument,options);
    }

    private byte[] getDocument(Producer producer, ProductOrder order)
    {
        byte[] output =null;
        try
        {
            File file = new File(FileUtility.getFullFilename(configuration.getSparkJava().getTempFolder(), getOrderDocumentFilename(producer, order)));
            FileInputStream stream = new FileInputStream(file);
            output = stream.readAllBytes();
        }
        catch (Exception ex)
        {

        }
        return output;
    }

}
