package com.datamelt.artikel.adapter.web.form;

import com.datamelt.artikel.adapter.web.MessageBundle;
import com.datamelt.artikel.adapter.web.validator.FormFieldValidator;
import com.datamelt.artikel.adapter.web.validator.ValidatorResult;

import java.util.Map;

public class ProductFormValidator
{
    public static ValidatorResult validate(ProductForm form, MessageBundle messages)
    {
        ValidatorResult result = null;
        for (final Map.Entry<ProductFormField, String> entry : form.getFields().entrySet())
        {
            boolean error = false;
            switch(entry.getKey().type())
            {
                case "long":
                    boolean longOk = FormFieldValidator.validateLong(entry.getValue());
                    if(!longOk)
                    {
                        return new ValidatorResult(ValidatorResult.RESULTYPE_ERROR,messages.get("FORM_FIELD_ERROR") + ": " + messages.get("PRODUCT_FORM_FIELD_" + entry.getKey()) + " - " + messages.get("FORM_FIELD_ERROR_LONG"));
                    }
                    break;
                case "double":
                    boolean doubleOk = FormFieldValidator.validateDouble(entry.getValue());
                    if(!doubleOk)
                    {
                        return new ValidatorResult(ValidatorResult.RESULTYPE_ERROR, messages.get("FORM_FIELD_ERROR") + ": " + messages.get("PRODUCT_FORM_FIELD_" + entry.getKey()) + " - " + messages.get("FORM_FIELD_ERROR_DOUBLE"));
                    }
                    break;
                case "int":
                    boolean intOk = FormFieldValidator.validateInteger(entry.getValue());
                    if(!intOk)
                    {
                        return new ValidatorResult(ValidatorResult.RESULTYPE_ERROR,messages.get("FORM_FIELD_ERROR") + ": " + messages.get("PRODUCT_FORM_FIELD_" + entry.getKey()) + " - " + messages.get("FORM_FIELD_ERROR_INTEGER"));
                    }
                    break;
            }
        }
        return new ValidatorResult("Alle Felder erfolgreich überprüft");
    }
}
