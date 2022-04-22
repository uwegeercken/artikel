package com.datamelt.artikel.adapter.web;

import com.datamelt.artikel.port.MessageBundleInterface;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class MessageBundle implements MessageBundleInterface
{
    private ResourceBundle messages;

    public MessageBundle(String languageTag) {
        Locale locale = languageTag != null ? new Locale(languageTag) : Locale.GERMAN;
        try
        {
            this.messages = ResourceBundle.getBundle("localization/messages", locale);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public String get(String message) {
        String msg;
        try
        {
            msg = messages.getString(message);
        }
        catch(Exception ex)
        {
            msg = messages.getString("NO_MESSAGE_DEFINED");
        }
        return msg;
    }

    @Override
    public final String get(final String key, final Object... args) {
        return MessageFormat.format(get(key), args);
    }
}
