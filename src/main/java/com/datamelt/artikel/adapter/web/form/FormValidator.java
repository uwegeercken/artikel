package com.datamelt.artikel.adapter.web.form;

import com.datamelt.artikel.adapter.web.validator.FormFieldValidator;
import com.datamelt.artikel.adapter.web.validator.ValidatorResult;
import com.datamelt.artikel.port.MessageBundleInterface;

import java.util.Map;

public class FormValidator
{
    public static ValidatorResult validate(Form form, MessageBundleInterface messages)
    {
        for (final Map.Entry<FormField, String> entry : form.getFields().entrySet())
        {
            switch(entry.getKey().type())
            {
                case "long":
                    boolean longOk = FormFieldValidator.validateLong(entry.getValue());
                    if(!longOk)
                    {
                        return new ValidatorResult(ValidatorResult.RESULTYPE_ERROR,messages.get("FORM_FIELD_ERROR") + ": " + messages.get("FORM_FIELD_" + entry.getKey()) + " - " + messages.get("FORM_FIELD_ERROR_LONG"));
                    }
                    break;
                case "int":
                    boolean intOk = FormFieldValidator.validateInteger(entry.getValue());
                    if(!intOk)
                    {
                        return new ValidatorResult(ValidatorResult.RESULTYPE_ERROR,messages.get("FORM_FIELD_ERROR") + ": " + messages.get("FORM_FIELD_" + entry.getKey()) + " - " + messages.get("FORM_FIELD_ERROR_INTEGER"));
                    }
                    break;
            }
        }
        return new ValidatorResult(messages.get("FORM_FIELD_NO_ERROR"));
    }

    public static ValidatorResult validateNotEMpty(Form form, MessageBundleInterface messages)
    {
        for (final Map.Entry<FormField, String> entry : form.getFields().entrySet())
        {
            if(entry.getKey().canBeEmpty()==false)
            {
                if(entry.getValue().trim().equals(""))
                {
                    return new ValidatorResult(ValidatorResult.RESULTYPE_ERROR,messages.get("FORM_FIELD_ERROR") + ": " + messages.get("FORM_FIELD_" + entry.getKey()) + " - " + messages.get("FORM_FIELD_EMPTY_ERROR"));
                }
            }
        }
        return new ValidatorResult(messages.get("FORM_FIELD_NO_ERROR"));
    }

    public static ValidatorResult validateUniqueness(Form form, MessageBundleInterface messages, boolean exists)
    {
        FormField uniqueField = null;
        for(final Map.Entry<FormField, String> entry : form.getFields().entrySet())
        {
            if(entry.getKey().unique())
            {
                uniqueField = entry.getKey();
                break;
            }
        }
        if (exists)
        {
            return new ValidatorResult(ValidatorResult.RESULTYPE_ERROR,messages.get("FORM_FIELD_ERROR") + ": " + messages.get("FORM_FIELD_" + uniqueField) + " - " + messages.get("FORM_FIELD_UNIQUE_NUMBER_ERROR"));
        }
        return new ValidatorResult(messages.get("FORM_FIELD_NO_ERROR"));
    }
}