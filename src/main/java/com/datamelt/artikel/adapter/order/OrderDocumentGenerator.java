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
import org.asciidoctor.SafeMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

import static org.asciidoctor.OptionsBuilder.options;

public class OrderDocumentGenerator implements OrderDocumentInterface
{
    private static final Logger logger = LoggerFactory.getLogger(OrderDocumentGenerator.class);
    private MainConfiguration configuration;

    public OrderDocumentGenerator(MainConfiguration configuration)
    {
        this.configuration = configuration;
    }

    @Override
    public byte[] getOrderDocument(Producer producer, ProductOrder order)
    {
        String inputFilename = "order_" + order.getProducerId() + ".adoc";
        String asciiDocument = getOrderTemplate(configuration.getAsciidoc().getDocumentsFolder(), inputFilename, order);

        generateDocument(inputFilename, asciiDocument);
        return getDocument(inputFilename);
    }

    private String getOrderTemplate(String folder, String filename, ProductOrder order)
    {
        Properties velocityProperties = new Properties();
        velocityProperties.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
        velocityProperties.put("file.resource.loader.path", folder);
        VelocityEngine engine = new VelocityEngine(velocityProperties);
        Context context = new VelocityContext();
        context.put("productorderitems", order.getOrderItems());
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

    private void generateDocument(String filename, String asciiDocument)
    {
        System.setProperty("jruby.compat.version", "RUBY1_9");
        System.setProperty("jruby.compile.mode", "OFF");
        final Asciidoctor asciidoctor = Asciidoctor.Factory.create();

        String pdfFilename = FileUtility.getFullFilename(configuration.getSparkJava().getTempFolder(), convertFilename(filename));
        Map<String, Object> options = options()
                .inPlace(true)
                .safe(SafeMode.UNSAFE)
                .toFile(new File( pdfFilename))
                .backend("pdf")
                .asMap();

        asciidoctor.convert(asciiDocument,options);
    }

    private byte[] getDocument(String filename)
    {
        byte[] output =null;
        try
        {
            File file = new File(FileUtility.getFullFilename(configuration.getSparkJava().getTempFolder(), convertFilename(filename)));
            FileInputStream stream = new FileInputStream(file);
            output = stream.readAllBytes();
        }
        catch (Exception ex)
        {

        }
        return output;
    }

    private String convertFilename(String filename)
    {
        return filename.replaceAll(".adoc", ".pdf");
    }
}
