package com.datamelt.artikel.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailConfiguration
{
    private String templateFileFolder;
    private String emailTemplateFilename;
    private String mailTransportProtocol;
    private String mailSmtpHost;
    private String mailSmtpPort;
    private String mailSender;
    private String mailSenderPassword;
    private String mailFrom;

    public String getEmailTemplateFilename() { return emailTemplateFilename; }
    public String getTemplateFileFolder() { return templateFileFolder; }
    public String getMailTransportProtocol() { return mailTransportProtocol; }
    public String getMailSmtpHost() { return mailSmtpHost; }
    public String getMailSmtpPort() { return mailSmtpPort; }
    public String getMailSender() { return mailSender; }
    public String getMailSenderPassword() { return mailSenderPassword; }
    public String getMailFrom() { return mailFrom; }
}
