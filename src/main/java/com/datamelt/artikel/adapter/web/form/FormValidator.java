package com.datamelt.artikel.adapter.web.form;

import com.datamelt.artikel.adapter.web.validator.FormFieldValidator;
import com.datamelt.artikel.adapter.web.validator.ValidatorResult;
import com.datamelt.artikel.app.web.util.NumberFormatter;
import com.datamelt.artikel.port.MessageBundleInterface;

import java.util.Map;

public class FormValidator
{
    private static ValidatorResult validateValues(Form form, MessageBundleInterface messages, NumberFormatter numberformatter)
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
                case "double":
                    boolean doubleOk = FormFieldValidator.validateDouble(entry.getValue(), numberformatter);
                    if(!doubleOk)
                    {
                        return new ValidatorResult(ValidatorResult.RESULTYPE_ERROR, messages.get("FORM_FIELD_ERROR") + ": " + messages.get("FORM_FIELD_" + entry.getKey()) + " - " + messages.get("FORM_FIELD_ERROR_DOUBLE"));
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

    private static ValidatorResult validateNotEmpty(Form form, MessageBundleInterface messages)
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

    private static ValidatorResult validateUniqueness(Form form, MessageBundleInterface messages, boolean isUnique)
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
        if (!isUnique)
        {
            return new ValidatorResult(ValidatorResult.RESULTYPE_ERROR,messages.get("FORM_FIELD_ERROR") + ": " + messages.get("FORM_FIELD_" + uniqueField) + " - " + messages.get("FORM_FIELD_UNIQUE_ERROR"));
        }
        return new ValidatorResult(messages.get("FORM_FIELD_NO_ERROR"));
    }

    public static ValidatorResult validate(Form form, MessageBundleInterface messages, boolean isUnique, NumberFormatter numberformatter)
    {
        ValidatorResult validateNotEmpty = validateNotEmpty(form, messages);
        if(validateNotEmpty.getResultType() == ValidatorResult.RESULT_TYPE_OK)
        {
            ValidatorResult validateValues = validateValues(form, messages, numberformatter);
            if(validateValues.getResultType() == ValidatorResult.RESULT_TYPE_OK)
            {
                try
                {
                    ValidatorResult validateUnique = validateUniqueness(form, messages, isUnique);
                    return validateUnique;
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    return null;
                }
            }
            else
            {
                return validateValues;
            }
        }
        else
        {
            return validateNotEmpty;
        }
    }
}
