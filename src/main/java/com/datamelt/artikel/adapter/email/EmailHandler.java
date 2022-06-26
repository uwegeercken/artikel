package com.datamelt.artikel.adapter.email;

import com.datamelt.artikel.adapter.csv.CsvLoader;
import com.datamelt.artikel.config.EmailConfiguration;
import com.datamelt.artikel.config.MainConfiguration;
import com.datamelt.artikel.model.ProductOrder;
import com.datamelt.artikel.port.EmailApiInterface;
import com.datamelt.artikel.util.Constants;
import com.datamelt.artikel.util.FileUtility;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.apache.commons.lang.CharEncoding;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;

public class EmailHandler implements EmailApiInterface
{
    private static final Logger logger =  LoggerFactory.getLogger(EmailHandler.class);

    private static final String EMAIL_DATE_FORMAT = "dd.MM.yyyy";
    private static final SimpleDateFormat emailDateFormat = new SimpleDateFormat(EMAIL_DATE_FORMAT);
    private static final DecimalFormat formatOrderNumber = new DecimalFormat("0000");

    @Override
    public boolean send(ProductOrder order, String emailRecipient, MainConfiguration configuration)
    {
        String receiver = "uwe.geercken@web.de"; // emailRecipient

        Properties properties = new Properties();

        properties.put("mail.transport.protocol", configuration.getEmail().getMailTransportProtocol());
        properties.put("mail.smtp.host", configuration.getEmail().getMailSmtpHost());
        properties.put("mail.smtp.port", configuration.getEmail().getMailSmtpPort());
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.user", configuration.getEmail().getMailSender());
        properties.put("mail.smtp.password", configuration.getEmail().getMailSenderPassword());
        properties.put("mail.smtp.starttls.enable", "true");

        Session mailSession = Session.getInstance(properties, new Authenticator()
        {
            @Override
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(properties.getProperty("mail.smtp.user"),
                        properties.getProperty("mail.smtp.password"));
            }
        });

        Message message = new MimeMessage(mailSession);
        try
        {
            InternetAddress addressTo = new InternetAddress(receiver);
            message.setRecipient(Message.RecipientType.TO, addressTo);
            message.setFrom(new InternetAddress(configuration.getEmail().getMailFrom()));
            message.setSubject("Borgmeier Bestellung aus der Artikelverwaltung");

            BodyPart messageBodyPart1 = new MimeBodyPart();
            String generatedMessageBody = generateMessageBody(order, configuration.getEmail());
            messageBodyPart1.setContent(generatedMessageBody,"text/html");

            // creating second MimeBodyPart object
            BodyPart messageBodyPart2 = new MimeBodyPart();
            String orderPdfFilename = Constants.ORDER_FILENAME_PREFIX + formatOrderNumber.format(order.getId()) + Constants.PDF_FILENAME_EXTENSION;
            String fullOrderPdfFilename = FileUtility.getFullFilename(configuration.getAsciidoc().getPdfOutputFolder(),orderPdfFilename);
            DataSource source = new FileDataSource(fullOrderPdfFilename);
            messageBodyPart2.setDataHandler(new DataHandler(source));
            messageBodyPart2.setFileName(fullOrderPdfFilename);

            // creating MultiPart object
            Multipart multipartObject = new MimeMultipart();
            multipartObject.addBodyPart(messageBodyPart1);
            multipartObject.addBodyPart(messageBodyPart2);
            message.setContent(multipartObject);

            Transport.send(message);
            logger.debug("email sent for order: [{}]", orderPdfFilename);
            return true;
        }
        catch(Exception ex)
        {
            logger.error("error sending email for order: [{}], error: [{}]", order.getId(), ex.getMessage());
            return false;
        }
    }

    private String generateMessageBody(ProductOrder order, EmailConfiguration configuration)
    {
        Properties velocityProperties = new Properties();
        velocityProperties.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
        velocityProperties.put("file.resource.loader.path", configuration.getTemplateFileFolder());

        VelocityEngine engine = new VelocityEngine(velocityProperties);
        Context context = new VelocityContext();
        context.put("order", order);
        context.put("createddate", emailDateFormat.format(order.getTimestampCreatedDate()));
        context.put("orderdate", emailDateFormat.format(order.getTimestampOrderDate()));
        StringWriter writer = new StringWriter();

        boolean success = engine.mergeTemplate(configuration.getEmailTemplateFilename(), CharEncoding.UTF_8, context, writer );
        if(success)
        {
            return writer.toString();
        }
        else
        {
            return null;
        }
    }
}
