package com.datamelt.artikel.adapter.web.form;

import com.datamelt.artikel.adapter.web.MessageBundle;
import com.datamelt.artikel.adapter.web.form.ProductForm;
import com.datamelt.artikel.adapter.web.form.ProductFormField;
import com.datamelt.artikel.adapter.web.validator.FormFieldValidator;
import com.datamelt.artikel.adapter.web.validator.FormValidatorResult;

import java.util.Map;

public class ProductFormValidator
{
    public static FormValidatorResult validate(ProductForm form, MessageBundle messages)
    {
        FormValidatorResult result = null;
        for (final Map.Entry<ProductFormField, String> entry : form.getFields().entrySet())
        {
            boolean error = false;
            switch(entry.getKey().type())
            {
                case "long":
                    boolean longOk = FormFieldValidator.validateLong(entry.getValue());
                    if(!longOk)
                    {
                        return new FormValidatorResult(longOk,messages.get("FORM_FIELD_ERROR") + ": " + messages.get("PRODUCT_FORM_FIELD_" + entry.getKey()) + " - " + messages.get("FORM_FIELD_ERROR_LONG"));
                    }
                    break;
                case "double":
                    boolean doubleOk = FormFieldValidator.validateDouble(entry.getValue());
                    if(!doubleOk)
                    {
                        return new FormValidatorResult(doubleOk, messages.get("FORM_FIELD_ERROR") + ": " + messages.get("PRODUCT_FORM_FIELD_" + entry.getKey()) + " - " + messages.get("FORM_FIELD_ERROR_DOUBLE"));
                    }
                    break;
                case "int":
                    boolean intOk = FormFieldValidator.validateInteger(entry.getValue());
                    if(!intOk)
                    {
                        return new FormValidatorResult(intOk,messages.get("FORM_FIELD_ERROR") + ": " + messages.get("PRODUCT_FORM_FIELD_" + entry.getKey()) + " - " + messages.get("FORM_FIELD_ERROR_INTEGER"));
                    }
                    break;
            }
        }
        return new FormValidatorResult(true,"Alle Felder erfolgreich überprüft");
    }
}
