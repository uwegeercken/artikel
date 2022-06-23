package com.datamelt.artikel.config;

public class EmailConfiguration
{
    private String templateFileFolder;
    private String emailTemplateFilename;
    private String mailTransportProtocol;
    private String mailSmtpHost;
    private String mailSmtpPort;


    public String getEmailTemplateFilename() { return emailTemplateFilename; }
    public String getTemplateFileFolder() { return templateFileFolder; }
    public String getMailTransportProtocol() { return mailTransportProtocol; }
    public String getMailSmtpHost() { return mailSmtpHost; }
    public String getMailSmtpPort() { return mailSmtpPort; }
}
